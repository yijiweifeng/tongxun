package sample.client.cache;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/19
 * Time: 14:31
 * Description: No Description
 */
@Data
public class ChatMessageCache {

    private static ChatMessageCache chatMessageCache;

    //未接收单聊信息
    private Map<Long, Integer> userNoReceiveSingerMsgMap = new HashMap<>();

    //未接收群聊信息
    private Map<Long, Integer> userNoReceiveGroupMsgMap = new HashMap<>();

    public static ChatMessageCache getInstance(){
        if(null == chatMessageCache){
            synchronized (ChatMessageCache.class){
                if(null == chatMessageCache){
                    chatMessageCache = new ChatMessageCache();
                }
            }
        }
        return chatMessageCache;
    }

}
