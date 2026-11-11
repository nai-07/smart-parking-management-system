package backend.controller;

import backend.model.Payment;
import backend.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/ticket/{ticketId}")
    public Optional<Payment> getPaymentByTicket(
            @PathVariable Integer ticketId) {

        return paymentService.findByTicketId(ticketId);
    }

    @PostMapping
    public Payment createPayment(
            @RequestBody Payment payment) {

        return paymentService.savePayment(payment);
    }
}