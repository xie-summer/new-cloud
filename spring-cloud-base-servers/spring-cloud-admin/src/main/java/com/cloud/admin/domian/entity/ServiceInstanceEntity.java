package com.cloud.admin.domian.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/** @author summer 2018/6/22 */
@TableName("instance")
@Data
public class ServiceInstanceEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @TableField("serviceId")
  private String serviceId;
  @TableField("instanceInfoId")
  private String instanceInfoId;
  /** the hostname of the registered ServiceInstanceEntity */
  @TableField("host")
  private String host;
  /** the port of the registered ServiceInstanceEntity */
  @TableField("port")
  private int port;
  /** if the port of the registered ServiceInstanceEntity is https or not */
  @TableField("is_secure")
  private Integer isSecure;
  /** the service uri address */
  @TableField("uri")
  private String uri;
  /** the key value pair metadata associated with the service instance */
  @TableField("metadata")
  private String metadata;
}
