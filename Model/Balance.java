package Model;

import java.io.Serial;
import java.io.Serializable;

public class Balance implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int id;
    double amount_owed;
    double amount_paid;
    double balance;

    /**
     *
     * @param id the unique id of user
     * @param amount_owed value to be paid by payee
     * @param amount_paid value paid in total by the uploader
     * @param balance expense insert
     */
    public Balance(int id, double amount_owed, double amount_paid, double balance){
        this. id = id;
        this. amount_owed = amount_owed;
        this. amount_paid = amount_paid;
        this. balance = balance;
    }

    /**
     *
     * @return value to be paid by payee of the expense
     */
    public double getAmount_owed() {
        return amount_owed;
    }

    /**
     *
     * @return value paid by the uploader
     */
    public double getAmount_paid() {
        return amount_paid;
    }

    /**
     *
     * @return the balance situation
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @return the unique id of user
     */
    public int getId() {
        return id;
    }
}
