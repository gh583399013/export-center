package test;

import com.alibaba.fastjson.JSON;
import com.ft.business.param.MyOrderParam;
import com.ft.export.enums.ExportTypeEnum;

/**
 * @author mask
 * @date 2019/6/10 19:08
 * @desc
 */
public class TestMain {
    //private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        String jsonStr = "{\"orderNo\":\"SO123\"}";
        try {


            //MyOrderParam myOrderParam = JSON.parseObject(jsonStr, ExportTypeEnum.OMS_MY_ORDER_PAGE.getParamClz());

            MyOrderParam myOrderParam1 = JSON.parseObject(jsonStr, MyOrderParam.class);

//            Map<String, Class> map = new HashMap<>();
//            map.put("Asd", MyOrderParam.class);
//
//            MyOrderParam qwe = new MyOrderParam();
//
//            MyOrderParam myOrderParam = JSON.parseObject(jsonStr, ExportTypeEnum.OMS_MY_ORDER_PAGE.getParamClz());
//
//            MyOrderParam myOrderParam = JSON.parseObject(jsonStr, map.get("Asd"));
//
//
//            //Class.forName(“类的全限定名”)
//            Class.forName("business.param.MyOrderParam");
//            MyOrderParam myOrderParam = CommonUtil.getParam(jsonStr, ExportTypeEnum.OMS_MY_ORDER_PAGE.getParamClz());
//
//            MyOrderParam myOrderParam1 = applicationContext.getBean(MyOrderParam.class);
//
//            MyOrderParam myOrderParam = CommonUtil.getParam(jsonStr, CommonUtil.getParam());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
