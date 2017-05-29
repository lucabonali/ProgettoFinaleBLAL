package main.gui.game_view;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 * @author Andrea
 * @author Luca
 */
public class LorenzoImage extends ImageView {
    private RotateTransition rotateTransition;
    private ScaleTransition scaleTransition;

    public LorenzoImage(Image image) {
        super(image);
        setX(500);
        setY(500);

        rotateTransition = new RotateTransition(Duration.millis(1500));
        scaleTransition = new ScaleTransition(Duration.millis(1500));
        rotateTransition.setNode(this);
        rotateTransition.setByAngle(1080);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(4);
        scaleTransition.setToY(4);

        startAnimation();

    }

    public void startAnimation(){
        ParallelTransition parallelTransition = new ParallelTransition(this,rotateTransition,scaleTransition);
        parallelTransition.play();
    }

}
