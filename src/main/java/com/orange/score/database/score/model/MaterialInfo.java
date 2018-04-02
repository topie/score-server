package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_material_info")
public class MaterialInfo {
    /**
     * id:hidden
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 材料名:text
     */
    private String name;

    /**
     * 备注:textarea
     */
    private String note;

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

    /**
     * 获取id:hidden
     *
     * @return id - id:hidden
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id:hidden
     *
     * @param id id:hidden
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取材料名:text
     *
     * @return name - 材料名:text
     */
    public String getName() {
        return name;
    }

    /**
     * 设置材料名:text
     *
     * @param name 材料名:text
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取备注:textarea
     *
     * @return note - 备注:textarea
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置备注:textarea
     *
     * @param note 备注:textarea
     */
    public void setNote(String note) {
        this.note = note;
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