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
import com.corona.vo.CoronaInfoVO;
import com.corona.vo.CoronaRegionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class CoronaAPIController {
    @Autowired CoronaInfoService service;
    @Autowired CoronaRegionInfoService r_service;
    @Autowired CoronaRegionInfoMapper r_mapper;
    @GetMapping("/api/corona")
    public Map<String, Object> getCoronaInfo(
        @RequestParam String startDt,
        @RequestParam String endDt
    ) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") /* + "Service Key" */); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDt, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDt, "UTF-8")); /*검색할 생성일 범위의 종료*/

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
            
            CoronaInfoVO vo = new CoronaInfoVO();
            vo.setAccExamCnt(Integer.parseInt(getTagValue("accExamCnt", elem)));
            vo.setAccExamCompCnt(Integer.parseInt(getTagValue("accExamCompCnt", elem)));
            vo.setCareCnt(Integer.parseInt(getTagValue("careCnt", elem)));
            vo.setClearCnt(Integer.parseInt(getTagValue("clearCnt", elem)));
            vo.setDeathCnt(Integer.parseInt(getTagValue("deathCnt", elem)));
            vo.setDecideCnt(Integer.parseInt(getTagValue("decideCnt", elem)));
            vo.setExamCnt(Integer.parseInt(getTagValue("examCnt", elem)));
            vo.setResultNegCnt(Integer.parseInt(getTagValue("resutlNegCnt", elem)));
            
            Date dt = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            dt = transFormat.parse(getTagValue("createDt", elem));
            vo.setStateTime(dt);

            service.insertCoronaInfo(vo);
            
        }        

        NodeList headerList = doc.getElementsByTagName("header");
        Element headerElem = (Element)headerList.item(0);

        // Element elem2 = (Element)nList2;

        resultMap.put("status", true);
        resultMap.put("resultMsg", getTagValue("resultMsg", headerElem));
        resultMap.put("resultCode", getTagValue("resultCode", headerElem));

        return resultMap;
    }

    @GetMapping("/api/corona_region")
    public Map<String, Object> getCoronaRegion(
        @RequestParam String startDt,
        @RequestParam String endDt
    ) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=3CID6KRU4kjF4jvHanoFBLwycg6Htt86aVfgEOgBmAecshZIcO5EC9UM9FhVGwAX2Zf%2B%2FrxgsJeUfled1zNS0w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDt, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDt, "UTF-8")); /*검색할 생성일 범위의 종료*/
        
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
    
            CoronaRegionVO vo = new CoronaRegionVO();

            vo.setDeathCnt(Integer.parseInt(getTagValue("deathCnt", elem)));
            vo.setDefCnt(Integer.parseInt(getTagValue("defCnt", elem)));
            vo.setGubun(getTagValue("gubun", elem));
            vo.setIncDec(Integer.parseInt(getTagValue("incDec", elem)));
            vo.setIsolClearCnt(Integer.parseInt(getTagValue("isolClearCnt", elem)));
            vo.setIsolIngCnt(Integer.parseInt(getTagValue("isolIngCnt", elem)));
            vo.setLocalOccCnt(Integer.parseInt(getTagValue("localOccCnt", elem)));
            vo.setOverFlowCnt(Integer.parseInt(getTagValue("overFlowCnt", elem)));
            
            Date dt = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            dt = transFormat.parse(getTagValue("createDt", elem));
            vo.setCreateDt(dt);

            System.out.println(dt);

            r_service.insertCoronaRegionInfo(vo);
            
        }        

        NodeList headerList = doc.getElementsByTagName("header");
        Element headerElem = (Element)headerList.item(0);

        // Element elem2 = (Element)nList2;

        resultMap.put("status", true);
        resultMap.put("resultMsg", getTagValue("resultMsg", headerElem));
        resultMap.put("resultCode", getTagValue("resultCode", headerElem));

        return resultMap;
    }

    @GetMapping("/api/coronaInfo")
    public Map<String, Object> getCoronaInfo(
        // @RequestParam String date
    ) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<CoronaInfoVO> data = null;
        // if(date.equals("today")) {
            data = service.selectTodayCoronaInfo();
        // }

        resultMap.put("status", true);
        resultMap.put("data", data);

        return resultMap;
    }
    
    @GetMapping("/api/coronaRegionInfo")
    public Map<String, Object> getCoronaRegionInfo(
        @RequestParam String date
    ) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        List<CoronaRegionVO> data = null;
        if(date.equals("today")) {
            data = r_service.selectTodayCoronaRegionInfo();
        }

        resultMap.put("status", true);
        resultMap.put("data", data);

        return resultMap;
    }

    // @GetMapping("/test")
    // public void getTest() {
        
    // }

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
