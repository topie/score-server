package com.orange.score.module.score.thread;

import com.orange.score.common.utils.SmsUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SendTaskSingleThread implements Runnable {

    private List<Map> infoList;

    private String template;

    public SendTaskSingleThread() {
    }

    public SendTaskSingleThread(List<Map> infoList, String template) {
        this.infoList = infoList;
        this.template = template;
    }

    @Override
    public void run() {
        try {
            for (Map map : this.infoList) {
                String mobile = (String) map.get("self_phone");
                String name = (String) map.get("name");
                String content = this.template.replaceAll("__name__", name);
                SmsUtil.send(mobile, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
