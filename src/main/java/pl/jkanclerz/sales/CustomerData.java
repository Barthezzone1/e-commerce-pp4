package pl.jkanclerz.sales;

public class CustomerData {
    private final String fname;
    private final String lname;
    private final String email;

    public CustomerData(String fname, String lname, String email) {

        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public static CustomerData of(String fname, String lname, String email) {
        return new CustomerData(fname, lname, email);
    }
}
