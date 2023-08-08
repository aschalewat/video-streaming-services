package com.gluereply.videostreamingservices.data;

import java.util.List;
import java.util.Optional;

/**
 * a repository in charge of payment data
 */
public interface PaymentDao {

    /**
     * gets a payment for a given id
     * @param id of the payment we want to retrieve
     * @return a {@link Payment} from repository for the given id
     */
    Payment getPaymentById(long id);

    /**
     * gets a list of payments for the given username
     * @param username
     * @return a {@link Payment} of payments
     */
    List<Payment> getPaymentsByUsername(String username);

    /**
     * gets a list of payments for the given credit card
     * @param creditCard of the payments we want to get
     * @return a {@link Payment} of payments
     */
    List<Payment> getPaymentsByCreditCard(String creditCard);

    /**
     * gets the list of all saved payments
     * @return
     */
    List<Payment> getAll();

    /**
     * saves the given payment into the repository
     * @param payment
     */
    void save(Payment payment);

    void clear();
}
