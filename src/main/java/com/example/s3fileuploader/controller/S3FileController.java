package com.example.s3fileuploader.controller;

import com.example.s3fileuploader.model.BlockText;
import com.example.s3fileuploader.util.GenericGetResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.s3fileuploader.service.S3Service;
import com.example.s3fileuploader.util.GenericHttpResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class S3FileController {
    @Autowired
    S3Service s3Service;

    @GetMapping("/")
    public String healthCheck(){
        return "healthy";
    }

    @PostMapping("/saveBlock")
    public GenericHttpResponse saveBlock(@RequestBody BlockText blockText){
        try{
            s3Service.saveBlock(blockText);
        } catch(Exception e){
            return new GenericHttpResponse(HttpStatus.SC_NOT_ACCEPTABLE, blockText.getBlockId() + " not accepted");
        }
        return new GenericHttpResponse(HttpStatus.SC_OK, "block saved");
    }

    @PostMapping("/convertToFile")
    public GenericHttpResponse saveFile(@RequestBody BlockText blockText){
        try{
            s3Service.uploadText(blockText);
        } catch(Exception e){
            return new GenericHttpResponse(HttpStatus.SC_NOT_ACCEPTABLE, blockText.getBlockId() + " not accepted");
        }
        return new GenericHttpResponse(HttpStatus.SC_OK, "file saved");
    }

    @GetMapping("/getAll")
    public GenericGetResponse getAllBlocks(){
        try{
            return new GenericGetResponse(HttpStatus.SC_OK, s3Service.getAllBlocks());
        } catch(Exception e){
            return new GenericGetResponse(HttpStatus.SC_NOT_ACCEPTABLE, new ArrayList<>());
        }
    }


}
