package diana.soleil.hossein.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import androidx.annotation.NonNull;

public class Customer implements Serializable, Comparator<Customer> {

    private String firstname;
    private String lastname;
    private double phone;
    private int SIN;
    private Account account;

    public Customer(String firstname, String lastname, double phone, int SIN, Account account) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.SIN = SIN;
        this.account = account;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getPhone() {
        return phone;
    }

    public void setPhone(double phone) {
        this.phone = phone;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int compare(Customer o1, Customer o2) {
        return o1.getLastname().compareTo(o2.getLastname());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getSIN() == customer.getSIN();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSIN());
    }

    @NonNull
    @Override
    public String toString() {
        return firstname + " " + lastname + "\n" + account.getBalance() + "\n" ;
    }
}
