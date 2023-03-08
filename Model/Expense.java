package Model;

import java.io.Serializable;
import java.util.Objects;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    private double value;
    private String payerID;


    public Expense(String description, double value, String payerID){
        this.description= description;
        this.value= value;
        this.payerID= payerID;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public String getPayerID() {
        return payerID;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.value, value) == 0 && Objects.equals(description, expense.description) && Objects.equals(payerID, expense.payerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, value, payerID);
    }

    @Override
    public String toString(){
        return "Expense{" +
                "description='" + description + '\'' +
                ", value=" + value +
                ", payer='" + payerID + '\'' +
                '}';
    }
}
