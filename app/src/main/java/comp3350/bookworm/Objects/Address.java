package comp3350.bookworm.Objects;

public class Address {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String province;
    private String postalCode;

    public Address(){
        addressLine1 = "";
        addressLine2 = "";
        city = "";
        province = "";
        postalCode = "";
    }

    public Address(String addressLine1, String addressLine2, String city, String province, String postalCode){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }


    public void setAddressLine1(String addr1){
        addressLine1 = addr1;
    }

    public void setAddressLine2(String addr2){
        addressLine2 = addr2;
    }

    public void setCity(String c){
        city = c;
    }

    public void setProvince(String p){
        province = p;
    }

    public void setPostalCode(String pCode){
        postalCode = pCode;
    }

    public String getAddressLine1(){
        return addressLine1;
    }

    public String getAddressLine2(){
        return addressLine2;
    }

    public String getCity(){
        return city;
    }

    public String getProvince(){
        return province;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public boolean isSet(){
        boolean addressIsSet = false;
        if(!addressLine1.isEmpty() && !city.isEmpty() && !province.isEmpty() && !postalCode.isEmpty())
            addressIsSet = true;
        return addressIsSet;
    }

}
