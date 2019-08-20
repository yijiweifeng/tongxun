package sample.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 16:18
 * Description: No Description
 */
public class ApiUrlManager {

    private static String baseUrl;

    static {
        try {
            InputStream in = ApiUrlManager.class.getClassLoader().getResource("connect.properties").openStream();
            Properties prop = new Properties();
            prop.load(in);
            baseUrl = prop.getProperty("connect.api.url");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 所有用户列表
     * @return
     */
    public static String get_user_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_user_list");
    }

    /**
     * 注册
     * @return
     */
    public static String regiser() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/regiser");
    }

    /**
     * 登录
     * @return
     */
    public static String login() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/login");
    }

    /**
     * 添加好友
     * @return
     */
    public static String add_friend() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/add_friend");
    }

    /**
     * 获取好友列表
     * @return
     */
    public static String get_friend_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_friend_list");
    }

    /**
     * 创建群
     * @return
     */
    public static String add_group() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/add_group");
    }

    /**
     * 加入群
     * @return
     */
    public static String joinGroup() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/join_group");
    }

    /**
     * 获取我创建的群列表
     * @return
     */
    public static String get_my_cteate_group_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_my_cteate_group_list");
    }

    /**
     * 获取我加入的群列表
     * @return
     */
    public static String get_my_join_group_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_my_join_group_list");
    }

    /**
     * 获取某个群内的用户列表
     * @return
     */
    public static String get_group_friend_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_group_friend_list");
    }

    /**
     * 获取待接收信息列表
     * @return
     */
    public static String get_not_received_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_not_received_list");
    }

    /**
     * 获取某个好友历史消息
     * @return
     */
    public static String get_friend_info_list() {
        return baseUrl == null ? "" : (baseUrl + "/api/im/get_friend_info_list");
    }
}
