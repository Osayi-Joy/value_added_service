package com.digicore.billentbackofficeservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@ToString
public class GenericResponseDTO {

    private String code;
    private String message;
    private Object data;
    @JsonIgnore
    private HttpStatus status;
    private Map<String, Object> metaData;

    public GenericResponseDTO() {
    }

    public GenericResponseDTO(String code, String message, Object data, HttpStatus status, Map<String, Object> metaData) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
        this.metaData = metaData;
    }

    public GenericResponseDTO(String code, String message, Object data, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public GenericResponseDTO(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
