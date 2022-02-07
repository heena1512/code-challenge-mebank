package com.mebank.codechallenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.mebank.codechallenge.model.Transaction;
import com.mebank.codechallenge.util.CommonUtil;

@Service
public class FileProcessor {

    private static Scanner scanner = new Scanner(System.in);

    public void processFile() {

        String fileName = "/CodeChallengeInput/input.csv";

        Stream<String> streamedFile = null;
        try {
            streamedFile = getStreamfromFile(fileName);
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.exit(1);
        }
        List<Transaction> transactionList = transformStreamToTransactions(streamedFile);
        RelativeBalanceCalculator rbc = new RelativeBalanceCalculator(transactionList);

        System.out.println(calculateAndGetOutputOnUserInput(scanner, rbc));
        System.exit(0);

        // recursively call until exit - Needs to be done.

        try {
            Stream<String> transactions = Files.lines(Paths.get(fileName));
            transactions.forEach(transaction -> transaction.split("/,/"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }

    }

    private static Stream<String> getStreamfromFile(String fileNameWithPath) throws IOException {
        Stream<String> transactions = Files.lines(Paths.get(fileNameWithPath));
        return transactions;
    }

    static List<Transaction> transformStreamToTransactions(Stream<String> transactions) {
        List<Transaction> transactionList = new ArrayList<>();
        transactions.forEach(transaction ->  transactionList.add(createTransactionFromInput(transaction)));
        return transactionList;
    }

    static Transaction createTransactionFromInput(String inputLine){
        return new Transaction(inputLine.split(","));
    }

    static String calculateAndGetOutputOnUserInput(Scanner scanner, RelativeBalanceCalculator rbc){
        System.out.println("Enter the Account ID for Balance Calculation: ");
        String accountID = scanner.nextLine();
        System.out.println("Enter the From Date for Balance Calculation: ");
        LocalDateTime fromDate = CommonUtil.getLocalDateTimeFromString(scanner.nextLine());
        System.out.println("Enter the To Date for Balance Calculation: ");
        LocalDateTime toDate = CommonUtil.getLocalDateTimeFromString(scanner.nextLine());
        return rbc.calculateRelativeBalanceFor(accountID, fromDate, toDate);
    }
}
