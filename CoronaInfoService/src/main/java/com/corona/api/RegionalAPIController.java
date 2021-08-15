package com.corona.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.corona.service.CoronaRegionInfoService;
import com.corona.vo.CoronaInfoVO;
import com.corona.vo.CoronaRegionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionalAPIController {
    @Autowired CoronaRegionInfoService service;
    @GetMapping("/api/regional")
    public Map<String, Object> getRegionalInfo(
        @RequestParam String region,
        @RequestParam @Nullable String date
    ) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(date == null) {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(now);
        }

        CoronaRegionVO vo = service.selectRegionalCoronaInfo(date, region);

        resultMap.put("data", vo);
        resultMap.put("status", true);

        return resultMap;
    }
}
