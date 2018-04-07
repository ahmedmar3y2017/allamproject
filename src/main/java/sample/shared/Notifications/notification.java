/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shared.Notifications;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * @author ahmed mar3y
 */
public class notification {

    public notification(String image, String title, String text) {

        Notifications noti = Notifications.create().
                title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(image))))
                .hideAfter(Duration.seconds(20));
        noti.show();
    }


}
