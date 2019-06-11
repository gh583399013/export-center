package com.ft.business.impl;

import com.ft.business.api.IOrderService;
import com.ft.business.param.MyOrderParam;
import com.ft.business.resp.MyOrderPageResp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mask
 * @date 2019/6/11 13:48
 * @desc
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService{

    @Override
    public List<MyOrderPageResp> findByCondition(MyOrderParam myOrderParam) {
        System.out.println("进入实际的方法啦findByCondition");
        return null;
    }

    @Override
    public Long findCountByCondition(MyOrderParam myOrderParam) {
        System.out.println("进入实际的方法啦findCountByCondition");
        return 1000L;
    }

    @Override
    public Long findExportListCount(MyOrderParam myOrderParam) {
        System.out.println("进入实际的方法啦findExportListCount");
        return 100L;
    }

    @Override
    public List<MyOrderPageResp> findExportList(MyOrderParam myOrderParam) {
        System.out.println("进入实际的方法啦findExportList");
        return null;
    }
}
