package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id_payer;
    private double amount;
    private int day;
    private int month;
    private int year;
    private String description;

    public Expense(int id_payer, double amount,int day, int month, int year, String description){
        this.id_payer = id_payer;
        this.amount = amount;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getId_payer() {
        return id_payer;
    }

    public int getYear() {
        return year;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId_payer(int id_payer) {
        this.id_payer = id_payer;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
