package com.jj.summary.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import java.io.File;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.jj.summary.util.S3Uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Slf4j
public class FileController {
    
    private final S3Uploader s3Uploader;

    @PostMapping("/{userId}/upload")
    public String updateUserImage(@PathVariable("userId") String userId, 
    		@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, userId);
        } catch (Exception e) {
//        	return new ResponseEntity(HttpStatus.BAD_REQUEST);
        	e.printStackTrace();
        	return "error";
        }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
        return "test";
    }
    
    @GetMapping("/{userId}/getUserDir")
    public ListObjectsV2Result getUserDir(@PathVariable("userId") String userId) {
    	return s3Uploader.getS3(userId);
    }
    
}
