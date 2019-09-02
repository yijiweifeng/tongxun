package sample.client.cache;

import com.alibaba.fastjson.JSONObject;
import sample.client.utils.ApiUrlManager;
import sample.client.utils.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/21
 * Time: 9:19
 * Description: No Description
 */
public class AllUserInfoCache {

    private static AllUserInfoCache allUserInfoCache;

    private List<JSONObject> list = new ArrayList<>();

    public Map<Long,JSONObject> userMap = new HashMap<>();

    public Map<Long,JSONObject> getUserMap(){
        list.clear();
        userMap.clear();
        String get_user_list = ApiUrlManager.get_user_list();
        String post = HttpUtil.sendGet(get_user_list,"");
        JSONObject jsonObject = JSONObject.parseObject(post);
        if (jsonObject.getString("result") != null && jsonObject.getString("result").equals("200")) {
            list = (List<JSONObject>) (jsonObject.get("data"));
        }
        for(JSONObject object : list){
            userMap.put(object.getLong("id"),object);
        }
        return userMap;
    }

    public void clearMap(){
        userMap.clear();
        list.clear();
    }

    public static AllUserInfoCache getInstance(){
        if(null == allUserInfoCache){
            synchronized (AllUserInfoCache.class){
                if(null == allUserInfoCache){
                    allUserInfoCache = new AllUserInfoCache();
                }
            }
        }
        return allUserInfoCache;
    }

}
