package comp3350.bookworm.BusinessLogic.Validator;

import comp3350.bookworm.Objects.Payment;

public class PaymentValidator {
    public boolean isValidPmtOption(String issueNet, String cardNo, String cVV, String expiry) {
        return !(issueNet.isEmpty() || cardNo.isEmpty() || cVV.isEmpty() || expiry.isEmpty() || cardNo.length() < 4
                || cVV.length() < 3 || expiry.length() != 7 || expiry.substring(2,2).equals("/"));
    }

    public boolean isValidPmtOption(Payment p) {
        return !(p.getIssuingNetwork().isEmpty() || p.getCardNumber().isEmpty() || p.getcVV().isEmpty() || p.getExpiry().isEmpty() || p.getCardNumber().length() < 4
                || p.getcVV().length() < 3 || p.getExpiry().length() != 7 || p.getExpiry().substring(2,2).equals("/"));
    }

    public boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() >= 4;
    }

    public boolean isValidCvv(String cvv) {
        return cvv.length() >= 3;
    }

    public boolean isValidExpiry(String expiry) {
        return !(expiry.length() != 7 || expiry.substring(2,2).equals("/"));
    }
}
