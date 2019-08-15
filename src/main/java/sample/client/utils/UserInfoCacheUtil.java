package sample.client.utils;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 17:22
 * Description: No Description
 */
@Data
public class UserInfoCacheUtil {

    private int userId;

    private String userName;

    private long tel;

    private static UserInfoCacheUtil userInfoCacheUtil;

    public static UserInfoCacheUtil getInstance(){
        if(null == userInfoCacheUtil){
            synchronized (UserInfoCacheUtil.class){
                if(null == userInfoCacheUtil){
                    userInfoCacheUtil = new UserInfoCacheUtil();
                }
            }
        }
        return userInfoCacheUtil;
    }

}
