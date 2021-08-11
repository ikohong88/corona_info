package com.corona.api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.corona.mapper.CoronaRegionInfoMapper;
import com.corona.service.CoronaInfoService;
import com.corona.service.CoronaRegionInfoService;
import com.corona.service.GenAgeInfoService;
import com.corona.vo.GenAgeInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class GenAgeAPIController {
    @Autowired GenAgeInfoService service;
    @GetMapping("/api/gen_age_info")
    public Map<String, Object> insertGenAgeInfo(
    ) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=vggx5ZyUwDoqaQJmsFigTQfVolF3Jcu8paRmeMe5tSaCmeQ5MuvG%2FdHFafoKQjLYm0yS6zrWBlbwMdPykJa0qQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("3000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode("20210101", "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode("20210811", "UTF-8")); /*검색할 생성일 범위의 종료*/

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
        if(nList.getLength() <= 0) {
            resultMap.put("status", false);
            resultMap.put("message", "데이터가 없습니다.");
            return resultMap;
        }
        // System.out.println(nList.getLength());
        
        for(int i = 0; i < nList.getLength(); i++) {
            Element elem = (Element)nList.item(i);
            
            GenAgeInfoVO vo = new GenAgeInfoVO();
            vo.setConfCase(Integer.parseInt(getTagValue("confCase", elem)));
            vo.setConfCaseRate(Double.parseDouble(getTagValue("confCaseRate", elem)));

            String _createDt = getTagValue("createDt", elem);
			String[] createDt = _createDt.split(" ");

            vo.setCreateDt(createDt[0]);
            vo.setCriticalRate(Double.parseDouble(getTagValue("criticalRate", elem)));
            vo.setDeath(Integer.parseInt(getTagValue("death", elem)));
            vo.setDeathRate(Double.parseDouble(getTagValue("deathRate", elem)));
            // if(getTagValue("gubun", elem).equals("0-9")) {
            //     String Gubun = "0";
            //     vo.setGubun(Gubun);
            // }
            vo.setGubun(getTagValue("gubun", elem));            
            
            // Date dt = new Date();
            // SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // dt = transFormat.parse(getTagValue("createDt", elem));
            // vo.setStateTime(dt);

            service.insertGenAgeInfo(vo);
            
        }        

        NodeList headerList = doc.getElementsByTagName("header");
        Element headerElem = (Element)headerList.item(0);

        // Element elem2 = (Element)nList2;

        resultMap.put("status", true);
        resultMap.put("resultMsg", getTagValue("resultMsg", headerElem));
        resultMap.put("resultCode", getTagValue("resultCode", headerElem));

        return resultMap;
    }

    @GetMapping("/api/selectGenAge")
    public Map<String, Object> getGenAgeInfo(
        // @RequestParam String date
    ) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<GenAgeInfoVO> data = null;
        // if(date.equals("today")) {
            data = service.selectTodayGenAgeInfo();
        // }

        resultMap.put("status", true);
        resultMap.put("data", data);

        return resultMap;
    }

    public static String getTagValue(String tag, Element elem) {
        NodeList nlList = null;
        Node nValue = null;
        try {
            nlList = elem.getElementsByTagName(tag).item(0).getChildNodes();
            nValue = (Node) nlList.item(0);
        } catch(NullPointerException ne) {
            return "0";
        }
        if(nValue == null) return "0";
        return nValue.getNodeValue();
    }

}
