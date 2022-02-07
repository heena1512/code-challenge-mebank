package com.mebank.codechallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mebank.codechallenge.model.Transaction;
import com.mebank.codechallenge.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Tests that verify the calculator logic")
public class RelativeBalanceCalculatorTest {
    private static List<Transaction> transactionList = new ArrayList<>();
    private static List<Transaction> transactionListWithReturns = new ArrayList<>();

    @BeforeAll
    static void setupTransactionList(){
        transactionList.add(new Transaction("TX10001", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "8.50", "PAYMENT"));
        transactionList.add(new Transaction("TX10002", "ACC998877", "ACC778899", "20/10/2018 18:30:00", "12.75", "PAYMENT"));
        transactionList.add(new Transaction("TX10003", "ACC778899", "ACC998877", "20/10/2018 19:30:00", "6.35", "PAYMENT"));

        transactionListWithReturns.add(new Transaction("TX10001", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "8.50", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10002", "ACC998877", "ACC778899", "20/10/2018 18:30:00", "12.75", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10003", "ACC778899", "ACC998877", "20/10/2018 19:30:00", "6.35", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10004", "ACC998877", "ACC778899", "20/10/2018 19:30:00", "12.75", "REVERSAL", "TX10002"));
    }

    @DisplayName("Calculator should negate the Debit Calculations")
    @Test
    void testCalculatorForDebits(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionList);
        String value = rbc.calculateRelativeBalanceFor("ACC998877", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:50:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 18:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is -$21.25 and the Number of Transactions included is: 2", value);
    }

    @DisplayName("Calculator should be positive the Credit Calculations")
    @Test
    void testCalculatorForCredits(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionList);
        String value = rbc.calculateRelativeBalanceFor("ACC778899", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:50:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 18:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is $21.25 and the Number of Transactions included is: 2", value);
    }

    @DisplayName("Calculator should be calculate mix of Debit & Credit Calculations")
    @Test
    void testCalculatorForDebitsAndCredits(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionList);
        String value = rbc.calculateRelativeBalanceFor("ACC998877", CommonUtil.getLocalDateTimeFromString("20/10/2018 18:25:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is -$6.40 and the Number of Transactions included is: 2", value);
    }

    @DisplayName("Calculator should be calculate mix of Credit & Debit Calculations")
    @Test
    void testCalculatorForCreditAndDebits(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionList);
        String value = rbc.calculateRelativeBalanceFor("ACC778899", CommonUtil.getLocalDateTimeFromString("20/10/2018 18:25:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is $6.40 and the Number of Transactions included is: 2", value);
    }


    @DisplayName("Calculator should negate the Debit Calculations With Reversals")
    @Test
    void testCalculatorForDebitsWithReversals(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC998877", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:50:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 18:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is -$8.50 and the Number of Transactions included is: 1", value);
    }

    @DisplayName("Calculator should be positive the Credit Calculations with Reversals")
    @Test
    void testCalculatorForCreditsWithReversals(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC778899", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:50:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 18:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is $8.50 and the Number of Transactions included is: 1", value);
    }

    @DisplayName("Calculator should be calculate mix of Debit & Credit Calculations with Reversals")
    @Test
    void testCalculatorForDebitsAndCreditsWithReverSals(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC998877", CommonUtil.getLocalDateTimeFromString("20/10/2018 18:25:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is $6.35 and the Number of Transactions included is: 1", value);
    }

    @DisplayName("Calculator should be calculate mix of Credit & Debit Calculations with Reversals")
    @Test
    void testCalculatorForCreditAndDebitsWithReversals(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC778899", CommonUtil.getLocalDateTimeFromString("20/10/2018 18:25:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is -$6.35 and the Number of Transactions included is: 1", value);
    }

    @DisplayName("Calculator should be calculate all transactions with Reversals in Debit")
    @Test
    void testCalculatorForAllWithReversalsDebit(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC998877", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:55:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is -$2.15 and the Number of Transactions included is: 2", value);
    }

    @DisplayName("Calculator should be calculate all transactions with Reversals in Credit")
    @Test
    void testCalculatorForAllWithReversalsCredit(){
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        String value = rbc.calculateRelativeBalanceFor("ACC778899", CommonUtil.getLocalDateTimeFromString("20/10/2018 17:55:00"), CommonUtil.getLocalDateTimeFromString("20/10/2018 19:35:00"));
        Assertions.assertEquals("The Relative Balance for this period is $2.15 and the Number of Transactions included is: 2", value);
    }

}
