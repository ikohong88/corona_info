package com.corona.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.corona.mapper.GenAgeInfoMapper;
import com.corona.utils.DateUtils;
import com.corona.vo.GenAgeInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenAgeInfoService {
    @Autowired GenAgeInfoMapper mapper;
    public void insertGenAgeInfo(GenAgeInfoVO vo) {
        mapper.insertGenAgeInfo(vo);
    }
    public List<GenAgeInfoVO> selectTodayGenAgeInfo() {
        Calendar start = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        
        // start.set(Calendar.HOUR_OF_DAY, 10);
        // start.set(Calendar.MINUTE, 29);
        // start.set(Calendar.SECOND, 10);
        String _hour_min = DateUtils.leadingZero(start.get(Calendar.HOUR_OF_DAY))+DateUtils.leadingZero(start.get(Calendar.MINUTE));      
        
        String today = null;
        if(Integer.parseInt(_hour_min) >= 1532 ) {
            start.add(Calendar.DATE, -2);
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        } else {
            start.add(Calendar.DATE, -3);
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        }
        System.out.println(Integer.parseInt(_hour_min));
        System.out.println(today);

        // Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);
       
        List<GenAgeInfoVO> data = mapper.selectGenAgeInfoByDate(today); 
        
        for(GenAgeInfoVO item : data) {
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
