package com.gluereply.videostreamingservices.data;

//import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;

class PaymentDaoImplTest {

    private PaymentDaoImpl paymentDao;

    @BeforeEach
    void setup(){
        paymentDao = new PaymentDaoImpl();
    }



    @Test
    @DisplayName("should return a list of all payments in the repository")
    void getAll() {
        Payment payment = new Payment(1l, "david", "2343876412780987", 10);
        Payment payment1 = new Payment(1l, "james", "2343235412780987", 30);

        paymentDao.save(payment);
        paymentDao.save(payment1);

        List<Payment> payments = paymentDao.getAll();

        assertThat(payments).hasSize(2);

    }

    @Test
    @DisplayName("should save the given payment into repository")
    void save() {
        Payment payment = new Payment(1l, "david", "2343876412780987", 10);

        paymentDao.save(payment);

        List<Payment> actual = paymentDao.getAll();

        assertThat(actual).hasSize(1);

    }


}