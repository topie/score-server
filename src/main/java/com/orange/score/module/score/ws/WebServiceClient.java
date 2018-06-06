package com.orange.score.module.score.ws;

import com.alibaba.csb.ws.sdk.DumpSoapUtil;
import com.alibaba.csb.ws.sdk.WSClientSDK;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WebServiceClient {

    public void testWS2WSWithDispath() throws Exception {
        String ns = "http://hc.wsprocess.csb.alibaba.com/";

        String wsdlWS2WSAddr = "http://172.30.1.59:9081/juZhuZhengJiFen/1.0.0/ws2ws?wsdl";

        // Service Qname as defined in the WSDL.
        QName serviceName = new QName(ns, "WSHealthCheckServiceService");

        // Port QName as defined in the WSDL.
        QName portName = new QName(ns, "WSHealthCheckServicePort");

        // Create a dynamic Service instance
        Service service = Service.create(serviceName);

        // Add a port to the Service

        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, wsdlWS2WSAddr);

        // Create a dispatch instance
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

        // covert string to soap message

        String req = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.webinterface.yzym.si.sl.neusoft.com/\">\n"
                + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <ser:RsResidentJFRDBusinessRev>\n"
                + "         <!--ticket:-->\n" + "         <ser:arg0>NEUSERVICE_GGFW_TICKET_12</ser:arg0>\n"
                + "         <!--buzzNumb:-->\n" + "         <ser:arg1>TJZSYL_JFRDXT_001</ser:arg1>\n"
                + "         <!--sender:-->\n" + "         <ser:arg2>JFRDXT</ser:arg2>\n" + "         <!--reciver:-->\n"
                + "         <ser:arg3>TJZSYL</ser:arg3>\n" + "         <!--operatorName:-->\n"
                + "         <ser:arg4>经办人校验测试操作员</ser:arg4>\n" + "         <!--content:-->\n"
                + "         <ser:arg5><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ROOT><QUERY_PRAMS><idNumber>12010719660201662X</idNumber><busType>1</busType></QUERY_PRAMS></ROOT>]]></ser:arg5>\n"
                + "      </ser:RsResidentJFRDBusinessRev>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>";

        InputStream is = new ByteArrayInputStream(req.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);

        // 使用SDK给dispatch设置 ak和sk !!!
        dispatch = WSClientSDK.bind(dispatch, "ak", "sk", "PING", "vcsb.ws");
        System.out.println("Send out the request: " + req);

        // Invoke the endpoint synchronously
        // Invoke endpoint operation and read response
        SOAPMessage reply = dispatch.invoke(request);
        reply = dispatch.invoke(request);

        if (reply != null)
            System.out.println("Response from invoke:" + DumpSoapUtil.dumpSoapMessage("response", reply));
        else System.out.println("Response from invoke is null");
    }
}
