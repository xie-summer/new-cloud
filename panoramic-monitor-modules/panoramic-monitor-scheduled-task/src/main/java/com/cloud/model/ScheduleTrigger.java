package com.cloud.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author summer
 */
@Table(name = "schedule_triggers")
public class ScheduleTrigger extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * //时间表达式
     */

    @Column
    private String cron;
    /**
     * //使用状态 0：禁用   1：启用
     */

    private String status;
    /**
     * //任务名称
     */
    @Column(name = "job_name")
    private String jobName;
    /**
     * //描述
     */

    private String content;
    /**
     * //任务分组
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * @return
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
     * @return
     */
    public String getCron() {
        return cron;
    }

    /**
     * @param cron
     */
    public void setCron(String cron) {
        this.cron = cron;
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }


    /**
     * @return
     */
    @Override
    public Serializable realId() {
        return null;
    }

    /**
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
