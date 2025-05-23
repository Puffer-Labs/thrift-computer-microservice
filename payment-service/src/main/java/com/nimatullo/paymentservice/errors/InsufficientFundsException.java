package com.nimatullo.paymentservice.errors;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Payment provider declined; Insufficient funds.");
    }

}
