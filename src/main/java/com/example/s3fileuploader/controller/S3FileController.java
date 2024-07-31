package com.example.s3fileuploader.controller;

import com.example.s3fileuploader.model.BlockText;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.s3fileuploader.service.S3Service;
import com.example.s3fileuploader.util.GenericHttpResponse;

@RestController
@RequestMapping("api")
public class S3FileController {
    @Autowired
    S3Service s3Service;

    @GetMapping("/")
    public String healthCheck(){
        return "healthy";
    }

    @PostMapping("/save")
    public GenericHttpResponse saveFile(@RequestBody BlockText blockText){
        try{
            s3Service.saveBlock(blockText);
            s3Service.uploadText(blockText);
        } catch(Exception e){
            return new GenericHttpResponse(HttpStatus.SC_NOT_ACCEPTABLE, blockText.getBlockId() + " not accepted");
        }
        return new GenericHttpResponse(HttpStatus.SC_OK, "text saved");
    }


}
