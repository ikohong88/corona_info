package com.corona.compoment;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.corona.service.CoronaVaccineService;
import com.corona.vo.CoronaVaccineVO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class CoronaVaccinInfoComponent {
	@Autowired CoronaVaccineService v_service;
    @Scheduled(cron = "0 50 9 * * *")
    public String getVaccineInfo() throws IOException {
		System.out.println("Start VaccineInfo Scheduled!");
		Date dt = new Date();
		SimpleDateFormat dtFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String today = dtFormatter.format(dt);

		StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/15077756/v1/vaccine-stat");
		urlBuilder.append("?"+URLEncoder.encode("page","UTF-8")+"="+URLEncoder.encode("1","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("perPage","UTF-8")+"="+URLEncoder.encode("3000","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("cond[baseDate::EQ]","UTF-8")+"="+today+"%2000%3A00%3A00");
		urlBuilder.append("&"+URLEncoder.encode("serviceKey","UTF-8")+
		"=vggx5ZyUwDoqaQJmsFigTQfVolF3Jcu8paRmeMe5tSaCmeQ5MuvG%2FdHFafoKQjLYm0yS6zrWBlbwMdPykJa0qQ%3D%3D");
				
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		System.out.println("Response Code : " + conn.getResponseCode());
		System.out.println("Response Message : " + conn.getResponseMessage());
		
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}
		else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();		
		
		conn.disconnect();
		
		JSONObject jsonObj = new JSONObject(sb.toString());
		// Integer cnt = jsonObj.getInt("currentCount");
		// System.out.println(cnt);

		JSONArray dataArray = jsonObj.getJSONArray("data");
		for(int i=0;i<dataArray.length();i++) {
			JSONObject obj = dataArray.getJSONObject(i);
			Integer accumulatedSecondCnt = obj.getInt("accumulatedSecondCnt");
			Integer accumulatedFirstCnt = obj.getInt("accumulatedFirstCnt");
			String baseDate = obj.getString("baseDate");
			Integer firstCnt = obj.getInt("firstCnt");
			Integer secondCnt = obj.getInt("secondCnt");
			String sido = obj.getString("sido");
			Integer totalFirstCnt = obj.getInt("totalFirstCnt");
			Integer totalSecondCnt = obj.getInt("totalSecondCnt");
			
			CoronaVaccineVO vo = new CoronaVaccineVO();
			vo.setAccumulatedFirstCnt(accumulatedSecondCnt);
			vo.setAccumulatedSecondCnt(accumulatedFirstCnt);
			vo.setFirstCnt(firstCnt);
			vo.setSecondCnt(secondCnt);
			vo.setTotalFirstCnt(totalFirstCnt);
			vo.setTotalSecondCnt(totalSecondCnt);
			String[] _baseDate = baseDate.split(" ");
	
			vo.setBaseDate(_baseDate[0]);
			vo.setSido(sido);
	
			v_service.insertVaccineInfo(vo);
		}
		System.out.println("End VaccineInfo Scheduled!");
	return sb.toString();
	}	
}
