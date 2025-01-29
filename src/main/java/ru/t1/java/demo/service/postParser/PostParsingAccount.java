package ru.t1.java.demo.service.postParser;

import ru.t1.java.demo.model.Account;
import java.util.List;

public interface PostParsingAccount {
    List<Account> accountList (String accountPost);
}
