package sample.controller;

import javafx.fxml.Initializable;
import sample.fxmlinit.FxmlInitCtroller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/19
 * Time: 19:14
 * Description: No Description
 */
public class SuccessController implements Initializable {

    private FxmlInitCtroller fxmlInitCtroller = FxmlInitCtroller.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void hideModel(){
        fxmlInitCtroller.getSuccessStage().close();
        AddFriendController.addFriendController.getUserList();
    }

}
