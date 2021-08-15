package com.corona.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.corona.mapper.CoronaRegionInfoMapper;
import com.corona.utils.DateUtils;
import com.corona.vo.CoronaInfoVO;
import com.corona.vo.CoronaRegionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaRegionInfoService {
    @Autowired CoronaRegionInfoMapper mapper;
    public void insertCoronaRegionInfo(CoronaRegionVO vo) {
        mapper.insertCoronaRegionInfo(vo);
    }
    public List<CoronaRegionVO> selectTodayCoronaRegionInfo() {
        Calendar start = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        
        // start.set(Calendar.HOUR_OF_DAY, 10);
        // start.set(Calendar.MINUTE, 29);
        // start.set(Calendar.SECOND, 10);
        String _hour_min = DateUtils.leadingZero(start.get(Calendar.HOUR_OF_DAY))+DateUtils.leadingZero(start.get(Calendar.MINUTE));      
        
        String today = null;
        if(Integer.parseInt(_hour_min) >= 1031 ) {
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        } else {
            start.add(Calendar.DATE, -1);
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        }
        // System.out.println(Integer.parseInt(_hour_min));
        // System.out.println(today);

        // Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);

        return mapper.selectCoronaRegionInfoByDate(today);
    }

    
    public CoronaRegionVO selectRegionalCoronaInfo(String date, String region) {
        return mapper.selectRegionalCoronaInfo(date, region);
    }
    public CoronaInfoVO selectCoronaInfoRegionTotal(String date){
        return mapper.selectCoronaInfoRegionTotal(date);
    }
}
