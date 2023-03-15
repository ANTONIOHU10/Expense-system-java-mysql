package test.Model;

import Model.Expense;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void getDescription() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TEST",0);

        //condizione
        assertEquals("TEST",expense.getDescription());
    }

    @Test
    void getPayer_amount() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(3.00,expense.getPayer_amount());
    }

    @Test
    void getDay() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(15,expense.getDay());
    }

    @Test
    void getMonth() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(12,expense.getMonth());
    }

    @Test
    void getExpense_id() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(1,expense.getExpense_id());
    }

    @Test
    void getPayee_amount() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(4.00,expense.getPayee_amount());
    }

    @Test
    void getId_payer() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(2,expense.getId_payer());
    }

    @Test
    void getYear() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(2023,expense.getYear());
    }

    @Test
    void getIfPaid() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(0,expense.getIfPaid());
    }

    @Test
    void expense_id() {
        //creazione spesa
        Expense expense = new Expense(1,2,3.00,4.00,15,12,2023,"TESET",0);

        //condizione
        assertEquals(15,expense.getDay());
    }

}