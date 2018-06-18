package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "t_accept_address")
public class AcceptAddress {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select t_accept_address_seq.nextval from dual")
    private Integer id;

    /**
     * 地址:text
     */
    private String address;

    /**
     * 详细地址:textarea
     */
    @Column(name = "address_detail")
    private String addressDetail;

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
     * 获取地址:text
     *
     * @return address - 地址:text
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址:text
     *
     * @param address 地址:text
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取详细地址:textarea
     *
     * @return address_detail - 详细地址:textarea
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 设置详细地址:textarea
     *
     * @param addressDetail 详细地址:textarea
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
