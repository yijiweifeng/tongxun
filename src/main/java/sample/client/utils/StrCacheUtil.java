package sample.client.utils;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 16:16
 * Description: No Description
 */
@Data
public class StrCacheUtil {

    private String errorMsg;

    private static StrCacheUtil strCacheUtil;

    public static StrCacheUtil getInstance(){
        if(null == strCacheUtil){
            synchronized (StrCacheUtil.class){
                if(null == strCacheUtil){
                    strCacheUtil = new StrCacheUtil();
                }
            }
        }
        return strCacheUtil;
    }

}
