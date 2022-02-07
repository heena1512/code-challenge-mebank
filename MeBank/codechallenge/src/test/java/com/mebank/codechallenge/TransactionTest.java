package com.mebank.codechallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mebank.codechallenge.model.Transaction;

@DisplayName("Testing the Transaction Class")
public class TransactionTest {

    @DisplayName("The transaction constructor should return a transaction object")
    @Test
    void testTransactionConstructor(){
        Assertions.assertSame(Transaction.class, new Transaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "5.00", "PAYMENT").getClass());
    }

    @DisplayName("The transaction constructor should return a transaction object with no Related Transaction Information for PAYMENT")
    @Test
    void testTransactionConstructorWithNullRelatedTransaction(){
        Assertions.assertNull(new Transaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "5.00", "PAYMENT").getRelatedPayment());
    }

    @DisplayName("The transaction constructor should return a transaction object when run with REVERSAL")
    @Test
    void testTransactionConstructorWithARelatedTransaction(){
        Assertions.assertSame(Transaction.class, new Transaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "5.00", "REVERSAL", "TX10002").getClass());
    }

    @DisplayName("The transaction constructor should return a transaction object with a valid related info valuye with Related Transaction Information for REVERSAL")
    @Test
    void testTransactionConstructorValueOfWithARelatedTransaction(){
        Assertions.assertNotNull(new Transaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "5.00", "REVERSAL", "TX10002").getRelatedPayment());
    }

    @DisplayName("The transaction constructor should fail when there is no Related Transaction Information for a REVERSAL")
    @Test
    void testTransactionConstructorValueWithoutRelatedValueForReversal(){
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {new Transaction("TX10003", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "5.00", "REVERSAL").getClass();});
    }

}
