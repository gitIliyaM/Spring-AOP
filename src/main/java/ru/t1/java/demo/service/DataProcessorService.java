package ru.t1.java.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.aop.AroundDataProcessorService;
import ru.t1.java.demo.service.postParser.*;
import ru.t1.java.demo.service.jsonParser.*;
import ru.t1.java.demo.dto.*;
import ru.t1.java.demo.model.*;
import ru.t1.java.demo.repository.*;
import java.util.*;

@Service
public class DataProcessorService {
    private final JsonFileAccount jsonFileAccount;
    private final JsonFileTransaction jsonFileTransaction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PostStringAccount postStringAccount;
    private final PostStringTransaction postStringTransaction;

    @Autowired()
    public DataProcessorService(
        JsonFileAccount jsonFileAccount,
        JsonFileTransaction jsonFileTransaction,
        AccountRepository accountRepository,
        TransactionRepository transactionRepository,
        PostStringAccount postStringAccount,
        PostStringTransaction postStringTransaction
    ) {
        this.jsonFileAccount = jsonFileAccount;
        this.jsonFileTransaction = jsonFileTransaction;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.postStringAccount = postStringAccount;
        this.postStringTransaction = postStringTransaction;
    }
    @AroundDataProcessorService
    public List<AccountDTO> createAccountDTOFromFile() {
        AccountDTO[] arrayAccountDTO = jsonFileAccount.arrayAccountDTO();

        // AccountDTO[] arrayAccountDTO = null;
        // будет исключение throw new RuntimeException("файл пустой") для записи в БД "Errors"
        // для проверки можно убрать комментарии

        List<AccountDTO> accountDTOList = new ArrayList<>();
            if(arrayAccountDTO != null){
                for(AccountDTO account: arrayAccountDTO){
                    account.setIdClient(account.getIdClient());
                    account.setAccountType(account.getAccountType());
                    account.setBalance(account.getBalance());
                    accountDTOList.add(account);
                }
            } else {
                throw new RuntimeException("файл пустой");
            }
            return accountDTOList;
    }
    @AroundDataProcessorService
    public List<TransactionDTO> createTransactionDTOFromFile() {
        TransactionDTO[] arrayTransactionDTO = jsonFileTransaction.arrayTransactionDTO();

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        if(arrayTransactionDTO != null){
            for(TransactionDTO transaction: arrayTransactionDTO){
                transaction.setIdClient(transaction.getIdClient());
                transaction.setAmountTransaction(transaction.getAmountTransaction());
                transaction.setTimeTransaction(transaction.getTimeTransaction());
                transactionDTOList.add(transaction);
            }
        } else {
            throw new RuntimeException("файл пустой");
        }
        return transactionDTOList;
    }
    @AroundDataProcessorService
    public List<Account> createAccountFromFile() {
        List<Account> accountList = jsonFileAccount.arrayListAccount();
        for(Account account: accountList){
            try{
                accountRepository.saveAndFlush(account);
            } catch (RuntimeException ex){
                throw new RuntimeException(ex);
            }
        }
        return accountList;
    }
    @AroundDataProcessorService
    public List<Transaction> createTransactionFromFile() {
        List<Transaction> transactionList = jsonFileTransaction.arrayaListTransaction();
        for(Transaction transaction: transactionList){
            try{
                transactionRepository.saveAndFlush(transaction);
            } catch (RuntimeException ex){
                throw new RuntimeException(ex);
            }
        }
        return transactionList;
    }
    @AroundDataProcessorService
    public List<Account> createAccountPost(String accountPost)  {
        List<Account> accountList = postStringAccount.accountList(accountPost);

        for(Account account: accountList){
            try{
                // Account account = null;
                // accountRepository.saveAndFlush(account);
                // записать null приведет к исключения и записи ошибки в БД "Errors"
                // для проверки можно убрать комментарии
                accountRepository.saveAndFlush(account);
            } catch (RuntimeException ex){
                throw new RuntimeException(ex);
            }
        }
        return accountList;
    }
    @AroundDataProcessorService
    public List<Transaction> createTransactionPost(String transactionPost)  {
        List<Transaction> transactionList = postStringTransaction.transactionList(transactionPost);
        for(Transaction transaction: transactionList){
            try{
                transactionRepository.saveAndFlush(transaction);
            } catch (RuntimeException ex){
                throw new RuntimeException(ex);
            }
        }
        return transactionList;
    }
}
