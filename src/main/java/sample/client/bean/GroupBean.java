package sample.client.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/20
 * Time: 15:19
 * Description: No Description
 */
@Data
public class GroupBean {

    private Long id;

    private String groupName;

    private Integer groupType;

    private Long createUserId;

}
