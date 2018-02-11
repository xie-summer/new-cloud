package com.monitor.dto.productmaterials;

import lombok.Data;

import java.io.Serializable;

/**
 * @author gang
 */
@Data
public class PanoramicProductMaterialsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 时间段
     */
    private String hour;

    /**
     * 汇总值
     */
    private Double value;

}
