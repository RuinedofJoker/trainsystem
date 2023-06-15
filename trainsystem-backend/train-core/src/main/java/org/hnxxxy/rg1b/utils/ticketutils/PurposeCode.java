package org.hnxxxy.rg1b.utils.ticketutils;

public enum PurposeCode {

    PurposeCode_ADULT("ADULT"),
    PurposeCode_STUDENT("0X00")
    ;
    private final String text;

    PurposeCode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
