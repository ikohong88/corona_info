package com.corona.vo;

import lombok.Data;

@Data
public class GenAgeInfoVO {
    private Integer confCase;
    private double confCaseRate;
    private String createDt;
    private double criticalRate;
    private Integer death;
    private double deathRate;
    private String gubun;
};
