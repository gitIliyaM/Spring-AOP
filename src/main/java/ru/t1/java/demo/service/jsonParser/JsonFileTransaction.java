package ru.t1.java.demo.service.jsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonFileTransaction implements JsonParsingTransaction{
    private final File file = new File("src/main/resources/transactions.json");

    @Override
    public TransactionDTO[] arrayTransactionDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, TransactionDTO[].class);
        } catch (IOException ex){
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public List<Transaction> arrayaListTransaction()  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
