package com.tensquare.rabbit.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String telephone;
    private String email;
}
