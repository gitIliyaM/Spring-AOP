package ru.t1.java.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.buildBeansTopicsKafka.BuildBeansTopicsKafkaConfig;
import ru.t1.java.demo.configuration.kafkaProducer.KafkaCreateProducer;
import ru.t1.java.demo.dto.AccountDTO;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.jsonParser.JsonFileAccount;
import ru.t1.java.demo.service.jsonParser.JsonFileTransaction;
import ru.t1.java.demo.service.postParser.PostStringAccount;
import ru.t1.java.demo.service.postParser.PostStringTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class KafkaProducerProcessorService {

    private final JsonFileAccount jsonFileAccount;
    private final JsonFileTransaction jsonFileTransaction;
    private final KafkaCreateProducer kafkaCreateProducer;
    private final PostStringAccount postStringAccount;
    private final PostStringTransaction postStringTransaction;
    private final BuildBeansTopicsKafkaConfig buildBeansTopicsKafkaConfig;

    @Autowired
    KafkaProducerProcessorService(
        JsonFileAccount jsonFileAccount,
        JsonFileTransaction jsonFileTransaction,
        KafkaCreateProducer kafkaCreateProducer,
        PostStringAccount postStringAccount,
        PostStringTransaction postStringTransaction,
        BuildBeansTopicsKafkaConfig buildBeansTopicsKafkaConfig)
    {
        this.jsonFileAccount = jsonFileAccount;
        this.jsonFileTransaction = jsonFileTransaction;
        this.kafkaCreateProducer = kafkaCreateProducer;
        this.postStringAccount = postStringAccount;
        this.postStringTransaction = postStringTransaction;
        this.buildBeansTopicsKafkaConfig = buildBeansTopicsKafkaConfig;
    }
    public List<AccountDTO> kafkaAccountDTOFromFile() {
        AccountDTO[] arrayAccountDTO = jsonFileAccount.arrayAccountDTO();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringAccountDTO;

        for(AccountDTO account: arrayAccountDTO){
            account.setIdClient(account.getIdClient());
            account.setAccountType(account.getAccountType());
            account.setBalance(account.getBalance());
            accountDTOList.add(account);
            try {
                stringAccountDTO = objectMapper.writeValueAsString(account);
                kafkaProducer(stringAccountDTO,null, null, t1_demo_accounts().name());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return accountDTOList;
    }
    public List<Account> kafkaAccountFromFile(){
        List<Account> accountList = jsonFileAccount.arrayListAccount();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringAccount;
        for(Account account: accountList){
            try{
                stringAccount = objectMapper.writeValueAsString(account);
                kafkaProducer(null, stringAccount, null, t1_demo_accounts().name());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return accountList;
    }
    public List<Account> kafkaAccountPost(String accountStringPost){
        List<Account> accountList = postStringAccount.accountList(accountStringPost);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringAccountPost;
        for(Account accountPost: accountList){
            try{
                stringAccountPost = objectMapper.writeValueAsString(accountPost);
                kafkaProducer(null, null, stringAccountPost, t1_demo_accounts().name());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return accountList;
    }
    public List<TransactionDTO> kafkaTransactionDTOFromFile(){
        TransactionDTO[] arrayTransactionDTO = jsonFileTransaction.arrayTransactionDTO();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringTransactionDTO;

        for(TransactionDTO transaction: arrayTransactionDTO){
            transaction.setIdClient(transaction.getIdClient());
            transaction.setAmountTransaction(transaction.getAmountTransaction());
            transaction.setTimeTransaction(transaction.getTimeTransaction());
            transactionDTOList.add(transaction);
            try {
                stringTransactionDTO = objectMapper.writeValueAsString(transaction);
                kafkaProducer(stringTransactionDTO,null, null, t1_demo_transactions().name());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return transactionDTOList;
    }
    public List<Transaction> kafkaTransactionFromFile(){
        List<Transaction> transactionList = jsonFileTransaction.arrayaListTransaction();
        ObjectMapper objectMapper = new ObjectMapper();
        String stringTransaction;
        for(Transaction transaction: transactionList){
            try{
                stringTransaction = objectMapper.writeValueAsString(transaction);
                kafkaProducer(null, stringTransaction, null, t1_demo_transactions().name());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return transactionList;
    }
    public List<Transaction> kafkaTransactionPost(String transactionPost){
        List<Transaction> transactionList = postStringTransaction.transactionList(transactionPost);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringTransactionPost;
        for(Transaction transaction: transactionList){
            try{
                stringTransactionPost = objectMapper.writeValueAsString(transaction);
                kafkaProducer(null, null, stringTransactionPost, t1_demo_transactions().name());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return transactionList;
    }
    public void kafkaProducer(String stringDTO, String at, String atPost, String topicName){
        NewTopic t1_demo_account = t1_demo_accounts();
        ProducerRecord<String, String> producerRecord;
        KafkaProducer<String, String> kafkaProducer = kafkaCreateProducer.getKafkaProducer();

        if (stringDTO != null){
            producerRecord = new ProducerRecord<>(topicName, UUID.randomUUID().toString(), stringDTO);
        } else if (at != null){
            producerRecord = new ProducerRecord<>(topicName, UUID.randomUUID().toString(), at);
        } else {
            producerRecord = new ProducerRecord<>(topicName, UUID.randomUUID().toString(), atPost);
        }
        producerRecord(kafkaProducer,producerRecord);
    }
    public void producerRecord(KafkaProducer<String, String> kafkaProducer, ProducerRecord<String, String> producerRecord){
        try {
            RecordMetadata recordMetadata = kafkaProducer.send(producerRecord,
                ((metadata, exception) -> {
                    if (exception != null){
                        log.info("Ошибка при отправлении сообщения {}", exception.getMessage().toUpperCase());
                    } else {
                        log.info("Сообщение отправлено в Kafka {}", metadata.partition());
                    }
                })).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public NewTopic t1_demo_accounts(){
        return buildBeansTopicsKafkaConfig.t1_demo_accounts();
    }
    public NewTopic t1_demo_transactions(){
        return buildBeansTopicsKafkaConfig.t1_demo_transactions();
    }
}
