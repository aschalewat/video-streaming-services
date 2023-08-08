package com.gluereply.videostreamingservices.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PaymentDaoImpl implements PaymentDao {

    private List<Payment> payments = new ArrayList<>();

    @Override
    public Payment getPaymentById(long id) {
        return null;
    }

    @Override
    public List<Payment> getPaymentsByUsername(String username) {
        return Collections.emptyList();
    }

    @Override
    public List<Payment> getPaymentsByCreditCard(String creditCard) {
        return Collections.emptyList();
    }

    @Override
    public List<Payment> getAll() {
        return payments;
    }

    @Override
    public void save(Payment payment) {
        payments.add(payment);
    }

    @Override
    public void clear() {
        payments.clear();
    }
}
