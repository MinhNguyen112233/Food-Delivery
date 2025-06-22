package com.example.DATN.dto.res;

public class PublicVoucherResponse {
    private VoucherResponse voucherResponse;
    boolean isSave;

    public PublicVoucherResponse() {
    }

    public PublicVoucherResponse(VoucherResponse voucherResponse, boolean isSave) {
        this.voucherResponse = voucherResponse;
        this.isSave = isSave;
    }

    public VoucherResponse getVoucherResponse() {
        return voucherResponse;
    }

    public void setVoucherResponse(VoucherResponse voucherResponse) {
        this.voucherResponse = voucherResponse;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
