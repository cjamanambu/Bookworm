package comp3350.bookworm.Objects;

public class Payment {
    private String issuingNetwork;
    private String cardNumber;
    private String cVV;
    private String expiry;

    public Payment(){
        issuingNetwork = "";
        cardNumber = "";
        cVV = "";
        expiry = "";
    }

    public Payment(String issuingNetwork, String cardNumber, String cVV, String expiry){
        this.issuingNetwork = issuingNetwork;
        this.cardNumber = cardNumber;
        this.cVV = cVV;
        this.expiry = expiry;
    }

    public void setIssuingNetwork(String issuingNetwork){
        this.issuingNetwork = issuingNetwork;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void setcVV(String cVV){
        this.cVV = cVV;
    }

    public void setExpiry(String expiry){
        this.expiry = expiry;
    }

    public String getIssuingNetwork(){
        return issuingNetwork;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String truncateCardNumber(){
        return cardNumber.substring(cardNumber.length() - 4);
    }

    public String getcVV(){
        return cVV;
    }

    public String getExpiry(){
        return expiry;
    }

}
