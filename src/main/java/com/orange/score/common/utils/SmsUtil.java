package com.orange.score.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SmsUtil {

    public static void send(String mobile, String content) throws IOException {
        List<RequestPair> requestPairs = new ArrayList<>();
        requestPairs.add(RequestPairBuilder.build("SpCode", "222803"));
        requestPairs.add(RequestPairBuilder.build("LoginName", "taiji"));
        requestPairs.add(RequestPairBuilder.build("Password", "taiji1234"));
        requestPairs.add(RequestPairBuilder.build("MessageContent", content));
        requestPairs.add(RequestPairBuilder.build("UserNumber", mobile));
        requestPairs.add(RequestPairBuilder.build("SerialNumber", System.currentTimeMillis()));
        String result = OkHttpUtil.postForm("http://sms.api.ums86.com:8899/sms/Api/Send.do", requestPairs);
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        send("18600200791", "陈国俊的天津市居住证积分预约已经取消成功！");
    }
}
