package com.corona.compoment;

import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.corona.service.GenAgeInfoService;
import com.corona.vo.GenAgeInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class GenAgeInfoComponent {
    @Autowired GenAgeInfoService New_serivce;
    @Scheduled(cron="0 30 15 * * *")
    public void insertGenAgeInfo(
    ) throws Exception {
        System.out.println("Start GenAgeInfo Scheduled!");
        Date dt = new Date();
        SimpleDateFormat dtFormatter = new SimpleDateFormat("YYYYMMdd");
        String today = dtFormatter.format(dt);

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=vggx5ZyUwDoqaQJmsFigTQfVolF3Jcu8paRmeMe5tSaCmeQ5MuvG%2FdHFafoKQjLYm0yS6zrWBlbwMdPykJa0qQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 종료*/

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
        
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
            String gubun = getTagValue("gubun", elem);
            if (gubun.equals("0-9")) gubun = "0";
            else if(gubun.equals("10-19")) gubun="10";
            else if(gubun.equals("20-29")) gubun="20";
            else if(gubun.equals("20-39")) gubun="30";
            else if(gubun.equals("30-49")) gubun="40";
            else if(gubun.equals("40-59")) gubun="50";
            else if(gubun.equals("50-69")) gubun="60";
            else if(gubun.equals("60-79")) gubun="70";
            else if(gubun.equals("80 이상")) gubun="80";
            // if(getTagValue("gubun", elem).equals("0-9")) {
            //     String Gubun = "0";
            //     vo.setGubun(Gubun);
            // }
            // vo.setGubun(getTagValue("gubun", elem));            
            vo.setGubun(gubun);            
            
            // Date dt = new Date();
            // SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // dt = transFormat.parse(getTagValue("createDt", elem));
            // vo.setStateTime(dt);
            
            New_serivce.insertGenAgeInfo(vo);
        }     
        System.out.println("End GenAgeInfo Scheduled!");         
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
