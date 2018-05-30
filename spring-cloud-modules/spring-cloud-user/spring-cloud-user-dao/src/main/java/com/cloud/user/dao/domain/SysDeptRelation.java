package com.cloud.user.dao.domain;

import com.cloud.model.BaseObject;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author lengleng
 * @since 2018-01-22
 */
@Table(name = "sys_dept_relation")
public class SysDeptRelation extends BaseObject {

    private static final long serialVersionUID = 1L;
    /**
     * 祖先节点
     */
    private Integer ancestor;
    /**
     * 后代节点
     */
    private Integer descendant;

    @Override
    public Serializable realId() {
        return null;
    }

    public Integer getAncestor() {
        return ancestor;
    }

    public void setAncestor(Integer ancestor) {
        this.ancestor = ancestor;
    }

    public Integer getDescendant() {
        return descendant;
    }

    public void setDescendant(Integer descendant) {
        this.descendant = descendant;
    }


    @Override
    public String toString() {
        return "SysDeptRelation{" +
                ", ancestor=" + ancestor +
                ", descendant=" + descendant +
                "}";
    }
}
