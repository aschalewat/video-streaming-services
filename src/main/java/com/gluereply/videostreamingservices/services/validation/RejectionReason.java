package com.gluereply.videostreamingservices.services.validation;

public enum RejectionReason {

    INVALID_EMAIL,
    USERNAME_EXISTS,
    INVALID_PASSWORD,
    INVALID_USERNAME,
    EMPTY_FIELDS,
    UNDER_AGE,
    INVALID_CREDIT_CARD,
    INVALID_AMOUNT,
    UNREGISTERED_CREDIT_CARD;
}
