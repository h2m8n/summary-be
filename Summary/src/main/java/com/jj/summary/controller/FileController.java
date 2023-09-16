package com.jj.summary.controller;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
    public ListObjectsV2Result updateUserImage(@PathVariable("userId") String userId, 
    		@RequestParam("file") MultipartFile multipartFile,
    		@RequestParam("fileName") String fileName) {
        try {
            s3Uploader.uploadFiles(multipartFile, userId, fileName);
        } catch (Exception e) {
        	e.printStackTrace();
        	
        }
        return s3Uploader.getS3(userId);
    }
    
    @GetMapping("/{userId}/getUserDir")
    public ListObjectsV2Result getUserDir(@PathVariable("userId") String userId) {
    	return s3Uploader.getS3(userId);
    }
    
    @PostMapping("/{userId}/getSummary")
    public void getSummary(@PathVariable("userId") String userId, @RequestParam("fileName") String fileName) {
    	
    	// HttpClient 인스턴스 생성
        HttpClient httpClient = HttpClients.createDefault();

        // POST 요청을 보낼 URL 지정
        String url = "http://13.125.59.104:5000/ai/summarize"; // 요청을 보낼 URL로 변경하세요.

        // HttpPost 객체 생성
        HttpPost httpPost = new HttpPost(url);

        try {
            // POST 요청의 본문 데이터 설정
            String jsonPayload = "{\"fileUrl\":\"https://jj-summary-dev.s3.ap-northeast-2.amazonaws.com/"+userId+"/"+fileName+"\"}"; // JSON 형식의 데이터로 변경하세요.
            System.out.println("jsonPayload test = " + jsonPayload);
            StringEntity entity = new StringEntity(jsonPayload);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json"); // 요청 헤더 설정 (JSON인 경우)

            // POST 요청 보내기
            HttpResponse response = httpClient.execute(httpPost);

            // 응답 데이터 가져오기
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // 응답 처리
            System.out.println("응답 코드: " + response.getStatusLine().getStatusCode());
            System.out.println("응답 본문: " + responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
