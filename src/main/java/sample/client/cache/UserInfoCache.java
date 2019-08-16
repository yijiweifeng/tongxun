package sample.client.cache;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 17:22
 * Description: No Description
 */
@Data
public class UserInfoCache {

    private Long userId;

    private String userName;

    private Long tel;

    private static UserInfoCache userInfoCache;

    public static UserInfoCache getInstance(){
        if(null == userInfoCache){
            synchronized (UserInfoCache.class){
                if(null == userInfoCache){
                    userInfoCache = new UserInfoCache();
                }
            }
        }
        return userInfoCache;
    }
}
