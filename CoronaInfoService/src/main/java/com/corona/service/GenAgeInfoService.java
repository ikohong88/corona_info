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
    public List<GenAgeInfoVO> selectGenInfo(String dt) {
        return mapper.selectGenInfoByDate(dt);
    }
    public List<GenAgeInfoVO> selectAgeInfo(String dt) {
        return mapper.selectAgeInfoByDate(dt);
    }
    public List<GenAgeInfoVO> selectTodayGenInfo() {
        // Calendar start = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        
        // start.set(Calendar.HOUR_OF_DAY, 10);
        // start.set(Calendar.MINUTE, 29);
        // start.set(Calendar.SECOND, 10);
        // String _hour_min = DateUtils.leadingZero(start.get(Calendar.HOUR_OF_DAY))+DateUtils.leadingZero(start.get(Calendar.MINUTE));      
        
        // String today = null;
        // if(Integer.parseInt(_hour_min) >= 1532 ) {
        //     start.add(Calendar.DATE, -2);
        //     today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        // } else {
        //     start.add(Calendar.DATE, -3);
        //     today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        // }
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 15);
        standard.set(Calendar.MINUTE, 30);
        standard.set(Calendar.SECOND, 10);

        if(now.getTimeInMillis() < standard.getTimeInMillis()) {
            now.add(Calendar.DATE, -2);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dt = formatter.format(now.getTime());
        }
        now.add(Calendar.DATE, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());
        // System.out.println(Integer.parseInt(_hour_min));
        // System.out.println(today);

        // Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);        
        return mapper.selectGenInfoByDate(dt);
    }
    public List<GenAgeInfoVO> selectTodayAgeInfo() {
        // Calendar start = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        
        // start.set(Calendar.HOUR_OF_DAY, 10);
        // start.set(Calendar.MINUTE, 29);
        // start.set(Calendar.SECOND, 10);
        // String _hour_min = DateUtils.leadingZero(start.get(Calendar.HOUR_OF_DAY))+DateUtils.leadingZero(start.get(Calendar.MINUTE));      
        
        // String today = null;
        // if(Integer.parseInt(_hour_min) >= 1532 ) {
        //     start.add(Calendar.DATE, -2);
        //     today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        // } else {
        //     start.add(Calendar.DATE, -3);
        //     today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        // }
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 15);
        standard.set(Calendar.MINUTE, 30);
        standard.set(Calendar.SECOND, 10);

        if(now.getTimeInMillis() < standard.getTimeInMillis()) {
            now.add(Calendar.DATE, -2);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dt = formatter.format(now.getTime());
        }
        now.add(Calendar.DATE, -1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());
        // System.out.println(Integer.parseInt(_hour_min));
        // System.out.println(today);

        // Date now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);    
        
        return mapper.selectAgeInfoByDate(dt);
    }
}
