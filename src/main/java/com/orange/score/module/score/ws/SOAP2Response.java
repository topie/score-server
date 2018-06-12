package com.orange.score.module.score.ws;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;

import java.util.Iterator;

public class SOAP2Response extends VisitorSupport {

    private String appCode;

    private String unitName;

    private String errMsg;

    private String score = "";

    private String partnerScore = "";

    public static void main(String[] args) {
        String soapResponse =
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "   <soap:Body>"
                        + "<ns1:RsResidentJFRDBusinessRevResponse xmlns:ns1=\"http://service.webinterface.yzym.si.sl.neusoft.com/\">"
                        + "<return xmlns=\"http://service.webinterface.yzym.si.sl.neusoft.com/\"><![CDATA["
                        + "<ROOT>" + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201701</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201702</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201703</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201704</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201705</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201706</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201707</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201708</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201709</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201710</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201711</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>" + "<ROOT>"
                        + "<appCode>0</appCode>" + "<birthIns></birthIns>"
                        + "<errMsg>判定期内缴费信息中出现断缴、补缴、个人缴费情况，请予核实</errMsg>" + "<flag>2</flag>"
                        + "<injuryIns></injuryIns>" + "<medicalIns></medicalIns>"
                        + "<payBase></payBase>" + "<paymentYear>201712</paymentYear>"
                        + "<pesionIns></pesionIns>" + "<personNumber>1001080631</personNumber>"
                        + "<unemploymentIns></unemploymentIns>" + "<unitCode></unitCode>"
                        + "<unitName></unitName>" + "<unitNumber></unitNumber>" + "</ROOT>]]></return>"
                        + "  </ns1:RsResidentJFRDBusinessRevResponse>" + "   </soap:Body>" + "</soap:Envelope>";

        SOAP2Response util = new SOAP2Response();
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
