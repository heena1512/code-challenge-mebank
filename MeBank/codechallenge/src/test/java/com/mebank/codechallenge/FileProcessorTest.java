package com.mebank.codechallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mebank.codechallenge.model.Transaction;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

@DisplayName("Test the Code Challenge")
public class FileProcessorTest {

    @DisplayName("Say something about this")
    @Test
    void testTransformation(){
        Stream<String> stringStream = Mockito.mock(Stream.class);
        Assertions.assertSame(ArrayList.class, FileProcessor.transformStreamToTransactions(stringStream).getClass());
    }

    @DisplayName("Test conversion from String to Transaction")
    @Test
    void testCreateTransaction(){
        String input = "TX10001,ACC998877,ACC778899,20/10/2018 18:00:00,8.50,PAYMENT";
        Assertions.assertEquals(850l, FileProcessor.createTransactionFromInput(input).getAmountInCents());
    }

    @DisplayName("Should accept User input and give a result")
    @Test
    void testUserInputToCalculator(){
        List<Transaction> transactionListWithReturns = new ArrayList<>();
        transactionListWithReturns.add(new Transaction("TX10001", "ACC998877", "ACC778899", "20/10/2018 18:00:00", "8.50", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10002", "ACC998877", "ACC778899", "20/10/2018 18:30:00", "12.75", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10003", "ACC778899", "ACC998877", "20/10/2018 19:30:00", "6.35", "PAYMENT"));
        transactionListWithReturns.add(new Transaction("TX10004", "ACC998877", "ACC778899", "20/10/2018 19:30:00", "12.75", "REVERSAL", "TX10002"));

        ByteArrayInputStream in = new ByteArrayInputStream("ACC778899\n20/10/2018 17:55:00\n20/10/2018 19:35:00".getBytes());
        Scanner consoleInput = new Scanner(in);
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionListWithReturns);
        Assertions.assertEquals("The Relative Balance for this period is $2.15 and the Number of Transactions included is: 2", FileProcessor.calculateAndGetOutputOnUserInput(consoleInput, rbc));
    }
}


