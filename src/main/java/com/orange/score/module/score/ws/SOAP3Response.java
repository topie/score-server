package com.orange.score.module.score.ws;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;

import java.util.Iterator;

public class SOAP3Response extends VisitorSupport {

    private String appCode;

    private String unitName;

    private String errMsg;

    private String score = "";

    private String partnerScore = "";

    public static void main(String[] args) {
        String soapResponse =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \n"
                        + "xmlns:ser=\"http://service.webinterface.yzym.si.sl.neusoft.com/\">\n"
                        + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <ser:RsResidentJFRDBusinessRev>\n"
                        + "         <!--ticket:-->\n" + "         <ser:arg0>NEUSERVICE_GGFW_TICKET_12</ser:arg0>\n"
                        + "         <!--buzzNumb:-->\n" + "         <ser:arg1>TJZSYL_JFRDXT_003</ser:arg1>\n"
                        + "         <!--sender:-->\n" + "         <ser:arg2>JFRDXT</ser:arg2>\n"
                        + "         <!--reciver:-->\n" + "         <ser:arg3>TJZSYL</ser:arg3>\n"
                        + "         <!--operatorName:-->\n" + "         <ser:arg4>自动打分操作员</ser:arg4>\n"
                        + "         <!--content:-->\n"
                        + "         <ser:arg5><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                        + "<ROOT><QUERY_PRAMS>"
                        + "<idNumber>449581890311122</idNumber>"
                        + "<partnerIdNnumber></partnerIdNnumber>"
                        + "<lessThan35>0</lessThan35>"
                        + "<canAdd>0</canAdd>"
                        + "<busType>3</busType>"
                        + "</QUERY_PRAMS></ROOT>]]></ser:arg5>\n"
                        + "</ser:RsResidentJFRDBusinessRev>\n" + "</soapenv:Body>\n" + "</soapenv:Envelope>";

        SOAP3Response util = new SOAP3Response();
        try {
            util.analysis(soapResponse);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println(util.getAppCode());
        System.out.println(util.getUnitName());
        System.out.println(util.getScore());

    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPartnerScore() {
        return partnerScore;
    }

    public void setPartnerScore(String partnerScore) {
        this.partnerScore = partnerScore;
    }

    public void visit(Element node) {
        if (node.getData() != null && StringUtils.isNotEmpty(node.getTextTrim())) {
            try {
                Document document = DocumentHelper.parseText(node.getTextTrim());
                Element info = document.getRootElement();
                Iterator infoIt = info.elementIterator();
                while (infoIt.hasNext()) {
                    Element element = (Element) infoIt.next();
                    if ("APPCODE".equals(element.getName().toUpperCase())) {
                        this.setAppCode(element.getText());
                    } else if ("UNITNAME".equals(element.getName().toUpperCase())) {
                        this.setUnitName(element.getText());
                    } else if ("SCORE".equals(element.getName().toUpperCase())) {
                        this.setScore(element.getText());
                    }else if ("PARTNERSCORE".equals(element.getName().toUpperCase())) {
                        this.setPartnerScore(element.getText());
                    }else if ("ERRMSG".equals(element.getName().toUpperCase())) {
                        this.setErrMsg(element.getText());
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

    }

    public void analysis(String soapContent) throws DocumentException {
        Document doc = DocumentHelper.parseText(soapContent);
        doc.accept(this);
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
