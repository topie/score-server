package com.orange.score.database.core.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "D_LOG")
public class Log {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select d_log_seq.nextval from dual")
    private Long id;

    @Column(name = "LOG_CONTENT")
    private String logContent;

    @Column(name = "LOG_USER")
    private String logUser;

    @Column(name = "LOG_TIME")
    private Date logTime;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return LOG_CONTENT
     */
    public String getLogContent() {
        return logContent;
    }

    /**
     * @param logContent
     */
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    /**
     * @return LOG_USER
     */
    public String getLogUser() {
        return logUser;
    }

    /**
     * @param logUser
     */
    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }

    /**
     * @return LOG_TIME
     */
    public Date getLogTime() {
        return logTime;
    }

    /**
     * @param logTime
     */
    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
