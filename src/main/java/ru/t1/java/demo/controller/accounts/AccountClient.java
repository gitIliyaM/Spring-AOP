package ru.t1.java.demo.controller.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.java.demo.service.DataProcessorService;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.*;
import ru.t1.java.demo.model.*;
import org.springframework.http.*;

import java.util.List;

@RestController
@RequestMapping("/ra")
public class AccountClient {

    private final DataProcessorService dataProcessorService;

    @Autowired
    public AccountClient(DataProcessorService dataProcessorService){
        this.dataProcessorService = dataProcessorService;

    }
    @GetMapping("/ga")
    public ResponseEntity<List<AccountDTO>> getAccountDTO()  {
        return new ResponseEntity<>(dataProcessorService.createAccountDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("gf")
    public ResponseEntity<List<Account>> createAccountFromFile()  {
        List<Account> accountList = dataProcessorService.createAccountFromFile();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @GetMapping("/gtx")
    public ResponseEntity<List<TransactionDTO>> getTransactionDTO() {
        return new ResponseEntity<>(dataProcessorService.createTransactionDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("gftx")
    public ResponseEntity<List<Transaction>> createTransactionFromFile()  {
        List<Transaction> transactionList = dataProcessorService.createTransactionFromFile();
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @PostMapping("/pa")
    public ResponseEntity<List<Account>> postAccountList(@RequestBody String account)  {
        List<Account> accountList = dataProcessorService.createAccountPost(account);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @PostMapping("/ptx")
    public ResponseEntity<List<Transaction>> postTransactionList(@RequestBody String transaction) {
        List<Transaction> transactionList = dataProcessorService.createTransactionPost(transaction);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }


}
