package com.corona.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.corona.mapper.CoronaInfoMapper;
import com.corona.utils.DateUtils;
import com.corona.vo.CoronaInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaInfoService {
    @Autowired CoronaInfoMapper mapper;
    public void insertCoronaInfo(CoronaInfoVO vo) {
        mapper.insertCoronaInfo(vo);
    }
    public List<CoronaInfoVO> selectTodayCoronaInfo() {
        Calendar start = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        
        // start.set(Calendar.HOUR_OF_DAY, 10);
        // start.set(Calendar.MINUTE, 10);
        // start.set(Calendar.SECOND, 10);

        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 10);
        standard.set(Calendar.MINUTE, 30);
        standard.set(Calendar.SECOND, 10);

        if(now.getTimeInMillis() < standard.getTimeInMillis()) {
            now.add(Calendar.DATE, -1);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dt = formatter.format(now.getTime());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());

        String _hour_min = DateUtils.leadingZero(start.get(Calendar.HOUR_OF_DAY))+DateUtils.leadingZero(start.get(Calendar.MINUTE));      
        
        String today = null;

        if(Integer.parseInt(_hour_min) >= 1031 ) {
            start.add(Calendar.DATE, -1);
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        } else {
            start.add(Calendar.DATE, -2);
            today = start.get(Calendar.YEAR)+"-"+DateUtils.leadingZero(start.get(Calendar.MONTH)+1)+"-"+DateUtils.leadingZero(start.get(Calendar.DATE));
        }
        // Date _now = new Date();
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // String date = formatter.format(now);

        System.out.println(today);

        List<CoronaInfoVO> data = mapper.selectCoronaInfoByDate(today);
        for(CoronaInfoVO item : data){
            Integer accExamCnt = item.getAccExamCnt();
            Integer decideCnt = item.getDecideCnt();
            
            DecimalFormat dFomatter = new DecimalFormat("###,###");
            String strAccExamCnt = dFomatter.format(accExamCnt);
            String strDecideCnt = dFomatter.format(decideCnt);
            
            item.setStrAccExamCnt(strAccExamCnt);
            item.setStrDecideCnt(strDecideCnt);
        }
        
        return data;
    }
}
