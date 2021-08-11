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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class CoronaVaccinInfoComponent {
	@Autowired CoronaVaccineService v_service;
    @Scheduled(cron = "0 50 9 * * *")
    public String getVaccineInfo() throws IOException {
		Date dt = new Date();
		SimpleDateFormat dtFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String today = dtFormatter.format(dt);

		StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/15077756/v1/vaccine-stat");
		urlBuilder.append("?"+URLEncoder.encode("page","UTF-8")+"="+URLEncoder.encode("1","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("perPage","UTF-8")+"="+URLEncoder.encode("3000","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("cond%5BbaseDate%3A%3AEQ%5D","UTF-8")+"="+URLEncoder.encode(today+"%2000%3A00%3A00","UTF-8"));
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
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(sb.toString());
			JSONArray dataArr = (JSONArray) jsonObj.get("data");
			
			for(int i = 0; i<dataArr.size(); i++) {
				JSONObject data = (JSONObject)dataArr.get(i);

				System.out.println("=======================================");
				// System.out.println("전일까지의 누적 통계 (1차 접종) : "+data.get("accumulatedFirstCnt"));
				// System.out.println("전일까지의 누적 통계 (2차 접종) : "+data.get("accumulatedSecondCnt"));
				System.out.println("통계 기준일자 : "+data.get("baseDate"));
				// System.out.println("당일 통계 (1차 접종) : "+data.get("firstCnt"));
				// System.out.println("당일 통계 (2차 접종) : "+data.get("secondCnt"));
				// System.out.println("지역명칭 : "+data.get("sido"));
				// System.out.println("전체 누적 통계 (1차 접종) : "+data.get("totalFirstCnt"));
				// System.out.println("전체 누적 통계 (2차 접종): "+data.get("totalSecondCnt"));
		
				CoronaVaccineVO vo = new CoronaVaccineVO();
				vo.setAccumulatedFirstCnt(Integer.parseInt(data.get("accumulatedFirstCnt").toString()));
				vo.setAccumulatedSecondCnt(Integer.parseInt(data.get("accumulatedSecondCnt").toString()));
				vo.setFirstCnt(Integer.parseInt(data.get("firstCnt").toString()));
				vo.setSecondCnt(Integer.parseInt(data.get("secondCnt").toString()));
				vo.setTotalFirstCnt(Integer.parseInt(data.get("totalFirstCnt").toString()));
				vo.setTotalSecondCnt(Integer.parseInt(data.get("totalSecondCnt").toString()));
				
				String _baseDate = data.get("baseDate").toString();
				String[] baseDate = _baseDate.split(" ");

				vo.setBaseDate(baseDate[0]);
				vo.setSido(data.get("sido").toString());

				v_service.insertVaccineInfo(vo);
			}
		}
		catch (ParseException pe) {
			pe.printStackTrace();
		}
		// catch (NumberFormatException ne) {
		// 	ne.printStackTrace();
		// }				
		return sb.toString();
	}	
}
