package com.orange.score.module.score.ws;

import com.alibaba.csb.ws.sdk.DumpSoapUtil;
import com.alibaba.csb.ws.sdk.WSClientSDK;
import org.dom4j.DocumentException;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebServiceClient {

    public static SOAP3Response action(String req) throws SOAPException, IOException, DocumentException {
        String ns = "http://service.webinterface.yzym.si.sl.neusoft.com/";
        String wsdlWS2WSAddr = "http://172.30.1.59:9081/juZhuZhengJiFen/1.0.0/ws2ws?wsdl";
        QName serviceName = new QName(ns, "NeuWebService");
        QName portName = new QName(ns, "NeuWebServicePortType");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, wsdlWS2WSAddr);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
        InputStream is = new ByteArrayInputStream(req.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
        dispatch = WSClientSDK
                .bind(dispatch, "3b12cb12fdf54a9296988ef3479fdf44", "j0R2XYM2mJjJ+dNNyc3rWdsVpAQ=", "juZhuZhengJiFen",
                        "1.0.0");
        System.out.println("Send out the request: " + req);
        SOAPMessage reply = dispatch.invoke(request);
        if (reply != null) {
            SOAP3Response response = new SOAP3Response();
            response.analysis(DumpSoapUtil.dumpSoapMessage("response", reply));
            return response;
        }
        return null;
    }


    public static String actionString(String req) throws SOAPException, IOException, DocumentException {
        String ns = "http://service.webinterface.yzym.si.sl.neusoft.com/";
        String wsdlWS2WSAddr = "http://172.30.1.59:9081/juZhuZhengJiFen/1.0.0/ws2ws?wsdl";
        QName serviceName = new QName(ns, "NeuWebService");
        QName portName = new QName(ns, "NeuWebServicePortType");
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, wsdlWS2WSAddr);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
        InputStream is = new ByteArrayInputStream(req.getBytes());
        SOAPMessage request = MessageFactory.newInstance().createMessage(null, is);
        dispatch = WSClientSDK
                .bind(dispatch, "3b12cb12fdf54a9296988ef3479fdf44", "j0R2XYM2mJjJ+dNNyc3rWdsVpAQ=", "juZhuZhengJiFen",
                        "1.0.0");
        System.out.println("Send out the request: " + req);
        SOAPMessage reply = dispatch.invoke(request);
        if (reply != null) {
            return DumpSoapUtil.dumpSoapMessage("response", reply);
        }
        return "";
    }


}
