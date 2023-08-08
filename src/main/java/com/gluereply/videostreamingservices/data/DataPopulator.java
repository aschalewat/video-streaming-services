package com.gluereply.videostreamingservices.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class DataPopulator {

    private UserDao userDao;
    private PaymentDao paymentDao;

    @Autowired
    public DataPopulator (UserDao userDao, PaymentDao paymentDao) {
        this.userDao = userDao;
        this.paymentDao = paymentDao;
    }

    @PostConstruct
    public void insertUserData(){
        userDao.save(new User("james", "Password12", "james@yahoo.com", LocalDate.parse("1990-12-31"), "1236458712364568"));
        userDao.save(new User("david", "Password12", "david@yahoo.com", LocalDate.parse("2001-04-15")));
        userDao.save(new User("john", "Password12", "john@yahoo.com", LocalDate.parse("1975-01-05"), "1236458712365678"));
        userDao.save(new User("monica", "Password12", "monica@yahoo.com", LocalDate.parse("1981-12-31")));
        userDao.save(new User("jane", "Password12", "jane@yahoo.com", LocalDate.parse("2000-06-19"), "1236458712369123"));
    }

    @PostConstruct
    public void insertPaymentData(){
        paymentDao.save(new Payment(1l, "james", "1236458712364568", 200));
        paymentDao.save(new Payment(2l, "john", "1236458712365678", 100));
        paymentDao.save(new Payment(3l, "jane", "1236458712369123", 150));

    }
}
