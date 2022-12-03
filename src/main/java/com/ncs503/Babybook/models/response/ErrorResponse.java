package com.ncs503.Babybook.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<String> trace;
}
