package diana.soleil.hossein.model;

import java.io.Serializable;
import java.util.Objects;

public class Account  implements Serializable {
    private double account_number;
    private String open_date;
    private float balance;

    public Account(double account_number, String open_date, float balance) {
        this.account_number = account_number;
        this.open_date = open_date;
        this.balance = balance;
    }

    public double getAccount_number() {
        return account_number;
    }

    public void setAccount_number(double account_number) {
        this.account_number = account_number;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getAccount_number() == account.getAccount_number() && getBalance() == account.getBalance() && Objects.equals(getOpen_date(), account.getOpen_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccount_number(), getOpen_date(), getBalance());
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_number=" + account_number +
                ", open_date='" + open_date + '\'' +
                ", balance=" + balance +
                '}';
    }
}
