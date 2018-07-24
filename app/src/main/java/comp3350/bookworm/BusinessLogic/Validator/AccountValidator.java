package comp3350.bookworm.BusinessLogic.Validator;

public class AccountValidator {

    public boolean isValidUsername(String username) {
        return username != null && !username.isEmpty();
    }

    public boolean isValidPassword(String password) {
        return password != null && !password.isEmpty();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidAddress(String addr_line_1, String city, String province, String post_code) {
        return !(addr_line_1.isEmpty() || city.isEmpty() || province.isEmpty() || post_code.isEmpty());
    }
}
