package test.Model;

import Model.Balance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {

    @Test
    void getAmount_owed() {
        //create balance
        Balance balance = new Balance(1,20.0,10.0,5.0);

        //condizione
        assertEquals(20.0,balance.getAmount_owed());

    }

    @Test
    void getAmount_paid() {
        //create balance
        Balance balance = new Balance(1,20.0,10.0,5.0);

        //condizione
        assertEquals(10.0,balance.getAmount_paid());
    }

    @Test
    void getBalance() {
        //create balance
        Balance balance = new Balance(1,20.0,10.0,5.0);

        //condizione
        assertEquals(5.0,balance.getBalance());
    }

    @Test
    void getId() {
        //create balance
        Balance balance = new Balance(1,20.0,10.0,5.0);

        //condizione
        assertEquals(1,balance.getId());
    }
}