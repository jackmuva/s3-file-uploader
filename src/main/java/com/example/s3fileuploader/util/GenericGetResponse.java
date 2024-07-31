package com.example.s3fileuploader.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericGetResponse {
    private Integer httpStatusCode;
    private Object response;
}
