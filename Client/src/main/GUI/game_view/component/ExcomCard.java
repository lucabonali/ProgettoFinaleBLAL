package main.GUI.game_view.component;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * @author Andrea
 * @author Luca
 */
public class ExcomCard extends ImageView{
    private String code;
    private AnchorPane container;
    private int period;

    public ExcomCard(GridPane pane, int period) {
        super();
        setFitWidth(79);
        setFitHeight(103);
        this.period = period;
        this.container = (AnchorPane) pane.getChildren().get(period-1);
        this.container.getChildren().add(this);
    }

    public String getCode() {
        return code;
    }

    public void setImage(Image image, String code) {
        setOpacity(0);
        new Thread(() -> {
            setImage(image);
            startShowAnimation();
        }).start();
        this.code = code;
    }

    /**
     * animazione che mi fa comparire la carta
     */
    private void startShowAnimation() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(5000), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
}
