package com.orange.score.database.score.model;

import javax.persistence.*;

@Table(name = "T_LUOHU")
public class Luohu4 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select T_LUOHU_SEQ4.nextval from dual")
    private Integer id;

    @Column(name = "PERSON_ID")
    private Integer personId;

    @Column(name = "TYPE")
    private Integer type;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return PERSON_ID
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * @param personId
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * @return TYPE
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
