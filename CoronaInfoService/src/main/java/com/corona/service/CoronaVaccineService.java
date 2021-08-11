package com.corona.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.corona.mapper.CoronaVaccineMapper;
import com.corona.utils.DateUtils;
import com.corona.vo.CoronaVaccineVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaVaccineService {
    @Autowired CoronaVaccineMapper mapper;
    public void insertVaccineInfo(CoronaVaccineVO vo) {
        mapper.insertVaccineInfo(vo);
    }
    public List<CoronaVaccineVO> selectTodayCoronaVaccineInfo() {
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 10);
        standard.set(Calendar.MINUTE, 10);
        standard.set(Calendar.SECOND, 10);

        if(now.getTimeInMillis() < standard.getTimeInMillis()) {
            now.add(Calendar.DATE, -1);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dt = formatter.format(now.getTime());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());

        // Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);
       
        List<CoronaVaccineVO> data = mapper.selectVaccineInfoByDate(dt); 
        
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for(CoronaVaccineVO item : data) {
            // String statusDt = formatter.format(item.getCreateDt());
            // Date createDt = item.getCreateDt();             
            // Integer deathCnt = item.getDeathCnt();
            // Integer defCnt = item.getDefCnt();
            // String gubun = item.getGubun();
            // Integer incDec = item.getIncDec();
            // Integer isolClearCnt = item.getIsolClearCnt();
            // Integer isolIngCnt = item.getIsolIngCnt();
            // Integer localOccCnt = item.getLocalOccCnt();
            // Integer overFlowCnt = item.getOverFlowCnt(); 
            // System.out.println(item.getCreateDt());   
        }
        return data;
    }
}
