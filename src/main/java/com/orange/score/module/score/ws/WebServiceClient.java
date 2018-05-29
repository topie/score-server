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

    private static final String ak = "ak";

    private static final String sk = "sk";

    private String host = System.getProperty("bhost");

    private String port = System.getProperty("broker.wsport", "9081");

    public void testWS2WSWithDispath() throws Exception {
        String ns = "http://hc.wsprocess.csb.alibaba.com/";

        String wsdlWS2WSAddr = String.format("http://%s:%s/PING/vcsb.ws/ws2ws?wsdl", host, port);

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

        String req = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:hc=\"http://hc.wsprocess.csb.alibaba.com/\"> \n" + "<soapenv:Header/>\n" + "<soapenv:Body>\n"
                + "<hc:ping>\n" + "<arg0>wiseking</arg0>\n" + "</hc:ping>\n" + "</soapenv:Body>\n"
                + "</soapenv:Envelope>";

        InputStream is = new ByteArrayInputStream(req.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);

        // 使用SDK给dispatch设置 ak和sk !!!
        dispatch = WSClientSDK.bind(dispatch, ak, sk, "PING", "vcsb.ws");
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
