package com.gluereply.videostreamingservices.data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private String email;
    private LocalDate dob;
    private String creditCard;

    public User() {
    }

    public User(String username, String password, String email, LocalDate dob, String creditCard) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.creditCard = creditCard;
    }

    public User(String username, String password, String email, LocalDate dob){
        this(username, password, email, dob, null );
    }

    public  static  class Builder{
       // required
        private String username;
        private String password;
        private String email;
        private LocalDate dob;
        // optional
        private String creditCard;

        public Builder(String username, String password, String email, LocalDate dob){
            this.username = username;
            this.password = password;
            this.email = email;
            this.dob = dob;
        }

        Builder creditCard(String creditCard){
            this.creditCard = creditCard;
            return this;
        }

        public User build(){
            return  new User(this);
        }

    }

    private User(Builder builder){
        username = builder.username;
        password = builder.password;
        email = builder.email;
        dob = builder.dob;
        creditCard = builder.creditCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return username.equals(that.username);
    }
}
