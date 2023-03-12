package Model;

import java.io.Serializable;

public class Balance implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    double amount_owed;
    double amount_paid;
    double balance;

    public Balance(int id, double amount_owed, double amount_paid, double balance){
        this.id = id;
        this. amount_owed = amount_owed;
        this. amount_paid = amount_paid;
        this. balance = balance;
    }

    public double getAmount_owed() {
        return amount_owed;
    }

    public double getAmount_paid() {
        return amount_paid;
    }

    public double getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }
}
