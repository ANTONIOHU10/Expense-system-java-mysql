package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 * expense used to be inserted
 */
public record Expense(int expense_id, int id_payer, double payer_amount, double payee_amount, int day, int month,
                      int year, String description, int ifPaid) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     * @return the information about the detail of an expense
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the value paid in total
     */
    public double getPayer_amount() {
        return payer_amount;
    }

    /**
     *
     * @return time information about the expense
     */
    public int getDay() {
        return day;
    }

    /**
     *
     * @return time information about the expense
     */
    public int getMonth() {
        return month;
    }

    /**
     *
     * @return the unique id of the expense
     */
    public int getExpense_id() {
        return expense_id;
    }

    /**
     *
     * @return value to be paid by the payee
     */
    public double getPayee_amount() {
        return payee_amount;
    }

    /**
     *
     * @return unique id of the user
     */
    public int getId_payer() {
        return id_payer;
    }

    /**
     *
     * @return time information about the expense
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @return 0 = not paid, 1 = paid
     */
    public int getIfPaid() {
        return ifPaid;
    }

}
