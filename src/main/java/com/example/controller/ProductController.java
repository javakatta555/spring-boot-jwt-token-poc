package com.example.controller;

import com.example.util.JWTTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ProductController {

    @GetMapping(value = "/product")
        public String getProduct(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = JWTTokenUtils.generateToken(null);
        log.info("Token is :{}",token);
        httpHeaders.add("Authorization",token);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        restTemplate.exchange("http://localhost:8080/product/jwt", HttpMethod.GET,httpEntity,String.class);
            return "Hello I am product";
    }

    @GetMapping(value = "/product/jwt")
    public String getProductByToken(@RequestHeader String Authorization) throws Exception {
        if(Authorization.isEmpty()){
            return "Please provide JWT token to access product api.";
        }
        if(JWTTokenUtils.verifyToken(Authorization))
            return "Hello I am product with authentication";
        else return "Don't hack our api.";
    }
}
