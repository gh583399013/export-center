package test;

import com.alibaba.fastjson.JSON;
import com.ft.business.param.MyOrderParam;

/**
 * @author mask
 * @date 2019/6/11 10:18
 * @desc
 */
public class CommonUtil {
    public static <T>T getParam(String jsonStr, Class<T> clazz){
        T t = JSON.parseObject(jsonStr, clazz);
        return t;
    }

    public static Class getParam(){
        return MyOrderParam.class;
    }
}
