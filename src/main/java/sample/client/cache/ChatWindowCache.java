package sample.client.cache;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/16
 * Time: 13:39
 * Description: No Description
 */
@Data
public class ChatWindowCache {

    private Long fromId;

    private Long toId;

    private String name;

    private Long tel;

    private static ChatWindowCache chatWindowCache;

    public static ChatWindowCache getInstance(){
        if(null == chatWindowCache){
            synchronized (ChatWindowCache.class){
                if(null == chatWindowCache){
                    chatWindowCache = new ChatWindowCache();
                }
            }
        }
        return chatWindowCache;
    }

}
