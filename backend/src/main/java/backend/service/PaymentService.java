package backend.service;

import backend.model.Payment;
import backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Optional<Payment> findByTicketId(Integer ticketId) {
        return paymentRepository.findByTicket_TicketId(ticketId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}