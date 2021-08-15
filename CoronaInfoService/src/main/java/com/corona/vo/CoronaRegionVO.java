package com.corona.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CoronaRegionVO {
    private Date createDt;
    private Integer deathCnt;
    private Integer defCnt;
    private String gubun;
    private Integer incDec;
    private Integer isolClearCnt;
    private Integer isolIngCnt;
    private Integer localOccCnt;
    private Integer overFlowCnt;

    private Integer diff;
}
