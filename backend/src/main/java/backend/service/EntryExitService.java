package backend.service;

import backend.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class EntryExitService {

    private final TicketService ticketService;
    private final SlotService slotService;
    private final BillingService billingService;
    private final PaymentService paymentService;

    public EntryExitService(
            TicketService ticketService,
            SlotService slotService,
            BillingService billingService,
            PaymentService paymentService) {

        this.ticketService = ticketService;
        this.slotService = slotService;
        this.billingService = billingService;
        this.paymentService = paymentService;
    }

    public BigDecimal processExitByTicketId(
            Integer ticketId) {

        return processExit(ticketId, null);
    }

    public BigDecimal processExitByTicketId(
            Integer ticketId,
            String validationCode) {

        return processExit(ticketId, validationCode);
    }

    private BigDecimal processExit(
            Integer ticketId,
            String validationCode) {

        Ticket ticket =
                ticketService.findActiveTicketById(ticketId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Active ticket not found."
                                ));

        ticket.setExitTime(LocalDateTime.now());

        BigDecimal normalAmount =
                billingService.calculateFee(ticket);

        BigDecimal finalAmount =
                billingService.calculateFee(
                        ticket,
                        validationCode
                );

        BigDecimal discount =
                normalAmount.subtract(finalAmount);

        ticket.setStatus(TicketStatus.COMPLETED);

        Slot slot = ticket.getSlot();
        slot.setStatus(SlotStatus.FREE);

        slotService.saveSlot(slot);
        ticketService.saveTicket(ticket);

        Payment payment = new Payment();

        payment.setTicket(ticket);
        payment.setAmount(finalAmount);
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setDiscountAmount(discount);

        paymentService.savePayment(payment);

        return finalAmount;
    }

    public BigDecimal processExitByPlate(
            String plateNumber) {

        Ticket ticket =
                ticketService.findActiveTicketByPlate(
                        plateNumber.toUpperCase().trim()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Active ticket not found."
                        ));

        return processExitByTicketId(
                ticket.getTicketId()
        );
    }
}