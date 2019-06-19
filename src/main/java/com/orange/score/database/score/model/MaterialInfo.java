package com.orange.score.database.score.model;

import com.orange.score.common.utils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.*;

@Table(name = "t_material_info")
public class MaterialInfo {

    /**
     * id:hidden
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select t_material_info_seq.nextval from dual")
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
     * 材料标题ID
     */
    @Column(name = "title_id")
    private Integer titleId;

    @Column(name = "template_img")
    private String templateImg;

    @Column(name = "is_upload")
    private Integer isUpload;

    /**
     * 申请人“材料上传”页面顺序较乱，增加两个字段修正顺序
     * 1） 分类，1：必传材料；2：人力社保局材料；3:公安局材料；
     * 2） 分类，21：高级技工学校高级班相关材料；22：专业技术人员职业资格证书相关材料；23：技能职业资格证书相关材料；24：奖项及荣誉称号相关材料；25：其他；
     */
    @Column(name = "category")
    private String category;
    @Column(name = "category_renshe")
    private String categoryRenshe;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryRenshe() {
        return categoryRenshe;
    }

    public void setCategoryRenshe(String categoryRenshe) {
        this.categoryRenshe = categoryRenshe;
    }

    //排序字段
    @Column(name = "sortColumns")
    private Integer sortColumns;

    public Integer getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(Integer sortColumns) {
        this.sortColumns = sortColumns;
    }

    @Column(name = "materialInfoRole")
    private String materialInfoRole;

    @Column(name = "ARCHIVING_STATUS")
    private Integer archivingStatus;

    public Integer getArchivingStatus() {
        return archivingStatus;
    }

    public void setArchivingStatus(Integer archivingStatus) {
        this.archivingStatus = archivingStatus;
    }

    public String getMaterialInfoRole() {
        return materialInfoRole;
    }

    public void setMaterialInfoRole(String materialInfoRole) {
        this.materialInfoRole = materialInfoRole;
        if (materialInfoRole != null) {
            this.materialInfoRoleSet = new HashSet<>(Arrays.asList(CollectionUtil.StringToIntegerArr(materialInfoRole)));
        }
    }

    /**
     * 材料标题
     */
    private String title;

    @Transient
    private OnlinePersonMaterial onlinePersonMaterial;

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public String getTemplateImg() {
        return templateImg;
    }

    public void setTemplateImg(String templateImg) {
        this.templateImg = templateImg;
    }

    public OnlinePersonMaterial getOnlinePersonMaterial() {
        return onlinePersonMaterial;
    }

    public void setOnlinePersonMaterial(OnlinePersonMaterial onlinePersonMaterial) {
        this.onlinePersonMaterial = onlinePersonMaterial;
    }

    //可以查看该材料的权限集合
    @Transient
    private Set<Integer> materialInfoRoleSet;

    public Set getMaterialInfoRoleSet() {
        return materialInfoRoleSet;
    }

    public void setMaterialInfoRoleSet(Set<Integer> materialInfoRoleSet) {
        this.materialInfoRoleSet = materialInfoRoleSet;
        if (materialInfoRoleSet != null) {
            materialInfoRole = StringUtils.join(materialInfoRoleSet, ",");
        }
    }

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

    /**
     * 获取材料标题ID
     *
     * @return title_id - 材料标题ID
     */
    public Integer getTitleId() {
        return titleId;
    }

    /**
     * 设置材料标题ID
     *
     * @param titleId 材料标题ID
     */
    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    /**
     * 获取材料标题
     *
     * @return title - 材料标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置材料标题
     *
     * @param title 材料标题
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
