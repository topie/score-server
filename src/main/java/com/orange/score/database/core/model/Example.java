package com.orange.score.database.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "d_example")
public class Example {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文本:text
     */
    @Column(name = "o_text")
    private String oText;

    /**
     * 文本:hidden
     */
    @Column(name = "o_hidden")
    private String oHidden;

    /**
     * 日期:date
     */
    @Column(name = "o_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date oDate;

    /**
     * 时间:datetime
     */
    @Column(name = "o_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date oDatetime;

    /**
     * 图片:image
     */
    @Column(name = "o_image")
    private String oImage;

    /**
     * 附件:file
     */
    @Column(name = "o_file")
    private String oFile;

    /**
     * 树:tree
     */
    @Column(name = "o_tree")
    private Integer oTree;

    /**
     * 选择:select:[选择1#1,选择2#2,选择3#3]
     */
    @Column(name = "o_select")
    private Integer oSelect;

    /**
     * 单选:radioGroup:[选择1#1,选择2#2,选择3#3]
     */
    @Column(name = "o_radio")
    private Integer oRadio;

    /**
     * 多选:checkboxGroup:[选择1#1,选择2#2,选择3#3]
     */
    @Column(name = "o_checkbox")
    private String oCheckbox;

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
     * 获取文本:text
     *
     * @return o_text - 文本:text
     */
    public String getoText() {
        return oText;
    }

    /**
     * 设置文本:text
     *
     * @param oText 文本:text
     */
    public void setoText(String oText) {
        this.oText = oText;
    }

    /**
     * 获取文本:hidden
     *
     * @return o_hidden - 文本:hidden
     */
    public String getoHidden() {
        return oHidden;
    }

    /**
     * 设置文本:hidden
     *
     * @param oHidden 文本:hidden
     */
    public void setoHidden(String oHidden) {
        this.oHidden = oHidden;
    }

    /**
     * 获取日期:date
     *
     * @return o_date - 日期:date
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    public Date getoDate() {
        return oDate;
    }

    /**
     * 设置日期:date
     *
     * @param oDate 日期:date
     */
    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    /**
     * 获取时间:datetime
     *
     * @return o_datetime - 时间:datetime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getoDatetime() {
        return oDatetime;
    }

    /**
     * 设置时间:datetime
     *
     * @param oDatetime 时间:datetime
     */
    public void setoDatetime(Date oDatetime) {
        this.oDatetime = oDatetime;
    }

    /**
     * 获取图片:image
     *
     * @return o_image - 图片:image
     */
    public String getoImage() {
        return oImage;
    }

    /**
     * 设置图片:image
     *
     * @param oImage 图片:image
     */
    public void setoImage(String oImage) {
        this.oImage = oImage;
    }

    /**
     * 获取附件:file
     *
     * @return o_file - 附件:file
     */
    public String getoFile() {
        return oFile;
    }

    /**
     * 设置附件:file
     *
     * @param oFile 附件:file
     */
    public void setoFile(String oFile) {
        this.oFile = oFile;
    }

    /**
     * 获取树:tree
     *
     * @return o_tree - 树:tree
     */
    public Integer getoTree() {
        return oTree;
    }

    /**
     * 设置树:tree
     *
     * @param oTree 树:tree
     */
    public void setoTree(Integer oTree) {
        this.oTree = oTree;
    }

    /**
     * 获取选择:select:[选择1#1,选择2#2,选择3#3]
     *
     * @return o_select - 选择:select:[选择1#1,选择2#2,选择3#3]
     */
    public Integer getoSelect() {
        return oSelect;
    }

    /**
     * 设置选择:select:[选择1#1,选择2#2,选择3#3]
     *
     * @param oSelect 选择:select:[选择1#1,选择2#2,选择3#3]
     */
    public void setoSelect(Integer oSelect) {
        this.oSelect = oSelect;
    }

    /**
     * 获取单选:radioGroup:[选择1#1,选择2#2,选择3#3]
     *
     * @return o_radio - 单选:radioGroup:[选择1#1,选择2#2,选择3#3]
     */
    public Integer getoRadio() {
        return oRadio;
    }

    /**
     * 设置单选:radioGroup:[选择1#1,选择2#2,选择3#3]
     *
     * @param oRadio 单选:radioGroup:[选择1#1,选择2#2,选择3#3]
     */
    public void setoRadio(Integer oRadio) {
        this.oRadio = oRadio;
    }

    public String getoCheckbox() {
        return oCheckbox;
    }

    public void setoCheckbox(String oCheckbox) {
        this.oCheckbox = oCheckbox;
    }
}
