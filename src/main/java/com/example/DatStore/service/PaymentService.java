package com.example.DatStore.service;

import com.example.DatStore.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    Payment createPayment(Payment payment);
    void deletePayment(Long id);
}
