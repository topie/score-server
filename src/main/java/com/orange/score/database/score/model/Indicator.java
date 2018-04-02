package com.orange.score.database.score.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_indicator")
public class Indicator {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类别
     */
    private String category;

    /**
     * 序号
     */
    @Column(name = "index_num")
    private Integer indexNum;

    /**
     * 指标
     */
    private String name;

    /**
     * 备注
     */
    private String note;

    /**
     * 选项类型  0：单选题，1：填空题
     */
    @Column(name = "item_type")
    private Integer itemType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建用户
     */
    @Column(name = "add_user")
    private String addUser;

    @Transient
    private List<Integer> material;

    @Transient
    private List<Integer> department;

    public List<Integer> getMaterial() {
        return material;
    }

    public void setMaterial(List<Integer> material) {
        this.material = material;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取类别
     *
     * @return category - 类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置类别
     *
     * @param category 类别
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取序号
     *
     * @return index_num - 序号
     */
    public Integer getIndexNum() {
        return indexNum;
    }

    /**
     * 设置序号
     *
     * @param indexNum 序号
     */
    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    /**
     * 获取指标
     *
     * @return name - 指标
     */
    public String getName() {
        return name;
    }

    /**
     * 设置指标
     *
     * @param name 指标
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取备注
     *
     * @return note - 备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置备注
     *
     * @param note 备注
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取选项类型  0：单选题，1：填空题
     *
     * @return item_type - 选项类型  0：单选题，1：填空题
     */
    public Integer getItemType() {
        return itemType;
    }

    /**
     * 设置选项类型  0：单选题，1：填空题
     *
     * @param itemType 选项类型  0：单选题，1：填空题
     */
    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建用户
     *
     * @return add_user - 创建用户
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * 设置创建用户
     *
     * @param addUser 创建用户
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }
}
