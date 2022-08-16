package homework3;

public class Person {

    private String phone;
    private String email;

    Person(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Phone = " + phone + ", email = " + email;
    }

    public String getPhone() {
        return "Phone: [ " + phone + " ]";
    }

    public String getEmail() {
        return "Email: [ " + email + " ]";
    }
}
