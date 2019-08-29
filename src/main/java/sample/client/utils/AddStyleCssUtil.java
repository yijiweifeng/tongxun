package sample.client.utils;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: joker
 * Date: 2019/8/29
 * Time: 15:36
 * Description: No Description
 */
public class AddStyleCssUtil {

    public static Stage addStyleCss(Stage stage){
        Scene scene = stage.getScene();
        scene.getStylesheets().addAll("/css/main.css","css/jfoenix-fonts.css","css/jfoenix-design.css");
        if(stage.getIcons() == null || stage.getIcons().size() == 0){
            stage.getIcons().add(new Image(AddStyleCssUtil.class.getClassLoader().getResourceAsStream("msg.png")));
        }
        stage.setScene(scene);
        return stage;
    }

    public static Scene addSceneStyle(Scene scene){
        scene.getStylesheets().addAll("/css/main.css","css/jfoenix-fonts.css","css/jfoenix-design.css");
        return scene;
    }

    public static Image addImageIcon(){
        return new Image(AddStyleCssUtil.class.getClassLoader().getResourceAsStream("msg.png"));
    }

}
