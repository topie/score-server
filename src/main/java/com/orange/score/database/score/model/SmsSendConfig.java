package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "t_sms_send_config")
public class SmsSendConfig {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_sms_send_config_seq.nextval from dual")
    private Integer id;

    /**
     * 状态字典alias
     */
    @Column(name = "status_dict_alias")
    private String statusDictAlias;

    /**
     * 状态值
     */
    @Column(name = "status_int")
    private Integer statusInt;

    /**
     * 短信模板内容
     */
    @Column(name = "template_str")
    private String templateStr;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取状态字典alias
     *
     * @return status_dict_alias - 状态字典alias
     */
    public String getStatusDictAlias() {
        return statusDictAlias;
    }

    /**
     * 设置状态字典alias
     *
     * @param statusDictAlias 状态字典alias
     */
    public void setStatusDictAlias(String statusDictAlias) {
        this.statusDictAlias = statusDictAlias;
    }

    /**
     * 获取状态值
     *
     * @return status_int - 状态值
     */
    public Integer getStatusInt() {
        return statusInt;
    }

    /**
     * 设置状态值
     *
     * @param statusInt 状态值
     */
    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    /**
     * 获取短信模板内容
     *
     * @return template_str - 短信模板内容
     */
    public String getTemplateStr() {
        return templateStr;
    }

    /**
     * 设置短信模板内容
     *
     * @param templateStr 短信模板内容
     */
    public void setTemplateStr(String templateStr) {
        this.templateStr = templateStr;
    }
}
