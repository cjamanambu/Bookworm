package comp3350.bookworm.Objects;

public class Account {
    private String userName;
    private String password;
    private String email;
    private Address address;
    private Payment paymentOption;

    public Account(String u, String p, String e) {
        userName = u;
        password = p;
        email = e;
        address = new Address();
        paymentOption = new Payment();
    }

    public Account(String u, String p) {
        userName = u;
        password = p;
        address = new Address();
        paymentOption = new Payment();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String u){this.userName = u;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String p){this.password = p;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String e){this.email = e;}

    public Address getAddress(){
        return address;
    }

    public void updateAddress(String adr1, String adr2, String c, String p, String pC){
        address.setAddressLine1(adr1);
        address.setAddressLine2(adr2);
        address.setCity(c);
        address.setProvince(p);
        address.setPostalCode(pC);
    }

    public Payment getPaymentOption(){
        return paymentOption;
    }

    public void updatePaymentOption(String issueNet, String cardNo, String cvv, String expiry){
        paymentOption.setIssuingNetwork(issueNet);
        paymentOption.setCardNumber(cardNo);
        paymentOption.setcVV(cvv);
        paymentOption.setExpiry(expiry);
    }

    public boolean equals(Account currentAccount) {
        return currentAccount.userName.equals(this.userName) && currentAccount.password.equals(this.password);
    }


}
