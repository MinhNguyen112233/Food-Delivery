package com.example.DATN.dto.req;


public class PaymentRequest{
    private String status;
    private String message;
    private String urlPayment;

    public PaymentRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlPayment() {
        return urlPayment;
    }

    public void setUrlPayment(String urlPayment) {
        this.urlPayment = urlPayment;
    }


}
