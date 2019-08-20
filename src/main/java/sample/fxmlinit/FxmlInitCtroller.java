package sample.fxmlinit;

import javafx.stage.Stage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/15
 * Time: 10:10
 * Description: No Description
 */
@Data
public class FxmlInitCtroller {

    private Stage stage = new Stage();

    private Stage addUserStage = new Stage();

    private Stage successStage = new Stage();

    private Stage errorStage = new Stage();

    private static FxmlInitCtroller fxmlInitCtroller;

    public static FxmlInitCtroller getInstance(){
        if( null == fxmlInitCtroller){
            synchronized (FxmlInitCtroller.class) {
                if( null == fxmlInitCtroller){
                    fxmlInitCtroller = new FxmlInitCtroller();
                }
            }
        }
        return fxmlInitCtroller;
    }

}
