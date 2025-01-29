package ru.t1.java.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Errors")
public class DataSourceErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column()
    private String exceptionStackTrace;

    @Column(length = 1024)
    private String message;

    @Column(length = 1024)
    private String signatureMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
       this.exceptionStackTrace = exceptionStackTrace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }
}
