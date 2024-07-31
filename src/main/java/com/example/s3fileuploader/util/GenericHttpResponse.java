package com.example.s3fileuploader.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericHttpResponse {
    private Integer httpStatusCode;
    private String message;


}