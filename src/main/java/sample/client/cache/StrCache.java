package sample.client.cache;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 16:16
 * Description: No Description
 */
@Data
public class StrCache {

    private String errorMsg;

    private static StrCache strCache;

    public static StrCache getInstance(){
        if(null == strCache){
            synchronized (StrCache.class){
                if(null == strCache){
                    strCache = new StrCache();
                }
            }
        }
        return strCache;
    }

}
