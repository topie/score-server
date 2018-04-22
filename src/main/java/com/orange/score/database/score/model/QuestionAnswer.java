package com.orange.score.database.score.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_common_question")
public class QuestionAnswer {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 常见问题
     */
    private String question;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 问题解答
     */
    private String answer;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取常见问题
     *
     * @return question - 常见问题
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 设置常见问题
     *
     * @param question 常见问题
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 获取创建时间
     *
     * @return c_time - 创建时间
     */
    public Date getcTime() {
        return cTime;
    }

    /**
     * 设置创建时间
     *
     * @param cTime 创建时间
     */
    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    /**
     * 获取问题解答
     *
     * @return answer - 问题解答
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置问题解答
     *
     * @param answer 问题解答
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}