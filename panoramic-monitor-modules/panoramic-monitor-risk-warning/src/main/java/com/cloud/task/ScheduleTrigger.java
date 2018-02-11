package com.cloud.task;

import java.util.Date;

import javax.persistence.*;

/**
 * @author summer
 */
@Table(name = "schedule_triggers")
public class ScheduleTrigger {
    @Id
    private Integer id;
    //间隔时间
    private Integer intervaltime;
    //执行时间
    private Date executetime;
    //执行sql文
    private String executesql;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Integer getIntervalTime() {
        return intervaltime;
    }

    public void setIntervalTime(Integer intervaltime) {
        this.intervaltime = intervaltime;
    }

    public Date getExecutetime() {
        return executetime;
    }

    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
    }

    public String getExecutesql() {
        return executesql;
    }

    public void setExecutesql(String executesql) {
        this.executesql = executesql;
    }

}
