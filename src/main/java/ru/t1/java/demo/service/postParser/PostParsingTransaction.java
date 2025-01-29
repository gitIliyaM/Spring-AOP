package ru.t1.java.demo.service.postParser;

import ru.t1.java.demo.model.Transaction;
import java.util.List;

public interface PostParsingTransaction {
    List<Transaction> transactionList (String accountPost);
}
