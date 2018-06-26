package com.cloud.admin.domian.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/** @author summer 2018/6/22 */
@Data
public class ServiceInstanceVo {
  private Integer id;

  private String serviceId;
  private String instanceInfoId;
  /** the hostname of the registered ServiceInstanceEntity */
  private String host;
  /** the port of the registered ServiceInstanceEntity */
  private int port;
  /** if the port of the registered ServiceInstanceEntity is https or not */
  private Integer isSecure;
  /** the service uri address */
  private String uri;
  /** the key value pair metadata associated with the service instance */
  private String metadata;
}
