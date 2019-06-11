package com.ft.business.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mask
 * @date 2019/6/10 17:56
 * @desc
 */
@Data
public class MyOrderPageResp {
    private String orderNo;
    private Date orderTime;
    private Integer orderStatus;
    private BigDecimal totalPrice;
    private Double DoublePride;
}
