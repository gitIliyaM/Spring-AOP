package ru.t1.java.demo.controller.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.adminKafka.*;
import ru.t1.java.demo.dto.AccountDTO;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.KafkaConsumerProcessorService;
import ru.t1.java.demo.service.KafkaProducerProcessorService;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaClient {

    //Протестировать создание топика, выполните HTTP-запрос:
    // http://localhost:8080/kafka/create-topic?topicName=my-topic&partitions=3&replicas=1

    //Протестировать удаление топика, выполните HTTP-запрос:
    // http://localhost:8080/kafka/delete-topic/my-topic

    private final CreateTopicPartition createTP;
    private final DeleteTopicPartition deleteTP;
    private final KafkaProducerProcessorService kafkaProducerProcessorService;
    private final KafkaConsumerProcessorService kafkaConsumerProcessorService;

    @Autowired
    public KafkaClient(
        CreateTopicPartition createTP,
        DeleteTopicPartition deleteTP,
        KafkaProducerProcessorService kafkaProducerProcessorService,
        KafkaConsumerProcessorService kafkaConsumerProcessorService
    ){
        this.createTP = createTP;
        this.deleteTP = deleteTP;
        this.kafkaProducerProcessorService = kafkaProducerProcessorService;
        this.kafkaConsumerProcessorService = kafkaConsumerProcessorService;
    }
    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(
        @RequestParam String topicName,
        @RequestParam int partitions,
        @RequestParam short replicas){
        return new ResponseEntity<>(createTP.createPostTopic(topicName, partitions, replicas), HttpStatus.OK);
    }
    @DeleteMapping("/delete-topic/{topicName}")
    public ResponseEntity<String> deleteTopic(@PathVariable String topicName){
        return new ResponseEntity<>(deleteTP.deletePostTopic(topicName), HttpStatus.OK);
    }
    @GetMapping("/ga")
    public ResponseEntity<List<AccountDTO>> getAccountDTO() {
        return new ResponseEntity<>(kafkaProducerProcessorService.kafkaAccountDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("gf")
    public ResponseEntity<List<Account>> createAccountFromFile()  {
        List<Account> accountList = kafkaProducerProcessorService.kafkaAccountFromFile();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @PostMapping("/pa")
    public ResponseEntity<List<Account>> postAccountList(@RequestBody String account)  {
        List<Account> accountList = kafkaProducerProcessorService.kafkaAccountPost(account);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @GetMapping("/gtx")
    public ResponseEntity<List<TransactionDTO>> getTransactionDTO() {
        return new ResponseEntity<>(kafkaProducerProcessorService.kafkaTransactionDTOFromFile(), HttpStatus.OK);
    }
    @GetMapping("gftx")
    public ResponseEntity<List<Transaction>> createTransactionFromFile()  {
        List<Transaction> transactionList = kafkaProducerProcessorService.kafkaTransactionFromFile();
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @PostMapping("/ptx")
    public ResponseEntity<List<Transaction>> postTransactionList(@RequestBody String transaction) {
        List<Transaction> transactionList = kafkaProducerProcessorService.kafkaTransactionPost(transaction);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
    @GetMapping("/cona")
    public ResponseEntity<String> getStringAccountConsumer(){
        String stringConsumerRecords = kafkaConsumerProcessorService.t1DemoAccountsConsumer();
        return new ResponseEntity<>(stringConsumerRecords, HttpStatus.OK);
    }
    @GetMapping("/cont")
    public ResponseEntity<String> getStringTransactionConsumer(){
        String stringConsumerRecords = kafkaConsumerProcessorService.t1DemoATransactionConsumer();
        return new ResponseEntity<>(stringConsumerRecords, HttpStatus.OK);
    }
}
