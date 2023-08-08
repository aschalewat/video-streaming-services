package com.gluereply.videostreamingservices.services.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private List<RejectionReason> rejectionReasons = new ArrayList<>();

    public List<RejectionReason> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<RejectionReason> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }
}
