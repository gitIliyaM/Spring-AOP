package ru.t1.java.demo.service.jsonParser;

import ru.t1.java.demo.dto.AccountDTO;
import ru.t1.java.demo.model.Account;
import java.util.List;

public interface JsonParsingAccount {
    AccountDTO[] arrayAccountDTO() ;
    List<Account> arrayListAccount() ;
}
