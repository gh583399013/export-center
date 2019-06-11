package com.ft.business.api;

import com.ft.business.param.MyOrderParam;
import com.ft.business.resp.MyOrderPageResp;
import com.ft.export.api.IExportCommonService;

import java.util.List;

/**
 * @author mask
 * @date 2019/6/10 17:55
 * @desc
 */
public interface IOrderService extends IExportCommonService<MyOrderParam, MyOrderPageResp> {
    /**
     * 按条件分页查询
     * @param myOrderParam
     * @return
     */
    List<MyOrderPageResp> findByCondition(MyOrderParam myOrderParam);

    /**
     * 按条件查总数量
     * @param myOrderParam
     * @return
     */
    Long findCountByCondition(MyOrderParam myOrderParam);
}
