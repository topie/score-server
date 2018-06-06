package com.orange.score.ws;

import com.alibaba.csb.ws.sdk.DumpSoapUtil;
import com.alibaba.csb.ws.sdk.WSClientSDK;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WebServiceClientTest {

    @Test
    public void testWS2WSWithDispath() throws Exception {
        String ns = "http://service.webinterface.yzym.si.sl.neusoft.com/";
        String wsdlWS2WSAddr = "http://172.30.1.59:9081/juZhuZhengJiFen/1.0.0/ws2ws?wsdl";
        QName serviceName = new QName(ns, "NeuWebService");
        QName portName = new QName(ns, "NeuWebServicePortType");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, wsdlWS2WSAddr);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
        String req =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.webinterface.yzym.si.sl.neusoft.com/\">"
                        + "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <ser:RsResidentJFRDBusinessRev>"
                        + "         <!--ticket:-->" + "         <ser:arg0>NEUSERVICE_GGFW_TICKET_12</ser:arg0>"
                        + "         <!--buzzNumb:-->" + "         <ser:arg1>TJZSYL_JFRDXT_001</ser:arg1>"
                        + "         <!--sender:-->" + "         <ser:arg2>JFRDXT</ser:arg2>"
                        + "         <!--reciver:-->" + "         <ser:arg3>TJZSYL</ser:arg3>"
                        + "         <!--operatorName:-->" + "         <ser:arg4>经办人校验测试操作员</ser:arg4>"
                        + "         <!--content:-->"
                        + "         <ser:arg5><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ROOT><QUERY_PRAMS><idNumber>12010719660201662X</idNumber><busType>1</busType></QUERY_PRAMS></ROOT>]]></ser:arg5>"
                        + "      </ser:RsResidentJFRDBusinessRev>" + "   </soapenv:Body>" + "</soapenv:Envelope>";
        InputStream is = new ByteArrayInputStream(req.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
        dispatch = WSClientSDK
                .bind(dispatch, "3b12cb12fdf54a9296988ef3479fdf44", "j0R2XYM2mJjJ+dNNyc3rWdsVpAQ=", "juZhuZhengJiFen",
                        "1.0.0");
        System.out.println("Send out the request: " + req);
        SOAPMessage reply = dispatch.invoke(request);
        if (reply != null)
            System.out.println("Response from invoke:" + DumpSoapUtil.dumpSoapMessage("response", reply));
        else System.out.println("Response from invoke is null");
    }
}
