package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_material_title")
public class MaterialTitle {
    /**
     * id:hidden
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_material_title_seq.nextval from dual")
    private Integer id;

    /**
     * 材料标题:text
     */
    private String title;

    /**
     * 创建时间:skip
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建用户:skip
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
     * 获取材料标题:text
     *
     * @return title - 材料标题:text
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置材料标题:text
     *
     * @param title 材料标题:text
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取创建时间:skip
     *
     * @return create_time - 创建时间:skip
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间:skip
     *
     * @param createTime 创建时间:skip
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建用户:skip
     *
     * @return add_user - 创建用户:skip
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * 设置创建用户:skip
     *
     * @param addUser 创建用户:skip
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }
}
