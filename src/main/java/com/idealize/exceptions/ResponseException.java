package com.idealize.exceptions;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

public class ResponseException implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Date timestamp;
    private String message;
    private String details;

    public ResponseException(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
