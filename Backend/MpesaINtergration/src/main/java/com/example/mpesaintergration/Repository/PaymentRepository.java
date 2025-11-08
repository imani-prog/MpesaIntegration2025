package com.example.mpesaintergration.Repository;

import com.example.mpesaintergration.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
