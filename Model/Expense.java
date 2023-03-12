package Model;

import java.io.Serializable;


public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private int expense_id;
    private int id_payer;
    private double payer_amount;
    private double payee_amount;
    private int day;
    private int month;
    private int year;
    private int ifPaid;
    private String description;

    public Expense(int expense_id,int id_payer, double payer_amount,double payee_amount,int day, int month, int year, String description,int ifPaid){
        this.expense_id = expense_id;
        this.id_payer = id_payer;
        this.payer_amount = payer_amount;
        this.payee_amount = payee_amount;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
        this.ifPaid = ifPaid;
    }

    public String getDescription() {
        return description;
    }

    public double getPayer_amount() {
        return payer_amount;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public double getPayee_amount() {
        return payee_amount;
    }

    public int getId_payer() {
        return id_payer;
    }

    public int getYear() {
        return year;
    }

    public int getIfPaid() {
        return ifPaid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
 }
