package com.example.community.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Orders implements Serializable {
    private Long orderId;

    private String goodsName;

    private BigDecimal price;

    private Integer status;


    /*@TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;*/

}
