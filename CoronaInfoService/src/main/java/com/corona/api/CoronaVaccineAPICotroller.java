package com.corona.api;

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
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoronaVaccineAPICotroller {
	@Autowired CoronaVaccineService service;
    @GetMapping("/api/vaccine")
    public String getVaccineInfo(
		@RequestParam String day
	) throws IOException {
		if (day == null) {
			Date dt = new Date();
			SimpleDateFormat dtFormatter = new SimpleDateFormat("YYYY-MM-dd");
			day = dtFormatter.format(dt);
		}				

		StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/15077756/v1/vaccine-stat");
		urlBuilder.append("?"+URLEncoder.encode("page","UTF-8")+"="+URLEncoder.encode("1","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("perPage","UTF-8")+"="+URLEncoder.encode("100000","UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("cond[baseDate::EQ]","UTF-8")+"="+day+"%2000%3A00%3A00");
		urlBuilder.append("&"+URLEncoder.encode("serviceKey","UTF-8")+
		"=vggx5ZyUwDoqaQJmsFigTQfVolF3Jcu8paRmeMe5tSaCmeQ5MuvG%2FdHFafoKQjLYm0yS6zrWBlbwMdPykJa0qQ%3D%3D");

		System.out.println(urlBuilder);
				
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
			// System.out.println(accumulatedSecondCnt);
			// System.out.println(accumulatedFirstCnt);
			// System.out.println(baseDate);
			// System.out.println(firstCnt);
			// System.out.println(secondCnt);
			// System.out.println(sido);
			// System.out.println(totalFirstCnt);
			// System.out.println(totalSecondCnt);

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
	
			service.insertVaccineInfo(vo);
		}
	return sb.toString();
	}

	@GetMapping("/api/vaccineinfo")
    public Map<String, Object> getCoronaInfo(
        // @RequestParam String date
    ) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<CoronaVaccineVO> data = null;
        // if(date.equals("today")) {
            data = service.selectTodayCoronaVaccineInfo();
        // }

        resultMap.put("status", true);
        resultMap.put("data", data);

        return resultMap;
    }
	
	public static String makeFileName(String name) {
   	    Calendar c = Calendar.getInstance();
        Integer y = c.get(Calendar.YEAR);
        Integer m = c.get(Calendar.MONTH)+1;
        Integer d = c.get(Calendar.DATE);
        Integer h = c.get(Calendar.HOUR_OF_DAY);
        Integer min = c.get(Calendar.MINUTE);
        
        String fileName = "";
        fileName += y;
        
//        if(m<10) fileName += "0"+m;
//        else fileName += m;
        
        fileName += m<10?"0"+m:m;
        fileName += d<10?"0"+d:d;
        fileName += h<10?"0"+h:h;
        fileName += min<10?"0"+min:min;
        fileName += "_"+name;
        
        return fileName;
   }
}
