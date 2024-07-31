package com.example.s3fileuploader.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.s3fileuploader.model.BlockText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.s3fileuploader.repository.BlockTextRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class S3Service {
    @Autowired
    AmazonS3 amazonS3;

    @Autowired
    BlockTextRepository blockTextRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);
    private static final String S3_BUCKET_NAME = "jacks-rag-bucket";



    private File convertMultiPartToFile(BlockText blockText) {
        File convFile = new File(blockText.getBlockId());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(blockText.getText().getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convFile;
    }


    public String generateFileName(BlockText blockText) {
        return new Date().getTime() + "-" + blockText.getBlockId();
    }

    public String uploadText(BlockText blockText) {
        File textFile = convertMultiPartToFile(blockText);
        String filename = generateFileName(blockText);
        try{
            PutObjectRequest putObjectRequest = new PutObjectRequest(S3_BUCKET_NAME, filename, textFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
            textFile.delete();
            return filename;
        } catch(Exception e){
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    public void saveBlock(BlockText blockText){
        blockTextRepository.save(blockText);
    }
}
