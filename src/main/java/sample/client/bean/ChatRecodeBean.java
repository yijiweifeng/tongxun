package sample.client.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/16
 * Time: 15:17
 * Description: No Description
 */
@Data
public class ChatRecodeBean {

    private Long id;
    private String infoId;
    private Long fromId;
    private Long toId;
    private Long sendTime;
    private Long receiveTime;
    private String content;
    private Integer contentType;
    private String uploadUrl;
    private Integer infoType;
    private Long groupId;
    private boolean isSend;


}
