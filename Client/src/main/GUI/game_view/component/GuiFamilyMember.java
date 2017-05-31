package main.GUI.game_view.component;

import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.GUI.Service;
import main.api.types.FamilyMemberType;
import main.client.AbstractClient;

/**
 * @author Luca
 * @author Andrea
 */
public class GuiFamilyMember extends ToggleButton {
    private static final double RADIUS = 15;
    private int id;
    private Color color;
    private FamilyMemberType familyMemberType;
    private Circle circle;
    private Rectangle rectangle;
    private boolean selected = false;

    public GuiFamilyMember(int id, FamilyMemberType familyMemberType) {
        super();
        this.id = id;
        this.familyMemberType = familyMemberType;
        setPrefSize(RADIUS*2, RADIUS*2);
        createPane();
    }

    private void createPane() {
        setStyle("-fx-border-color: black");
        Circle clip = new Circle(RADIUS);
        clip.setStrokeWidth(6);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        if (familyMemberType == FamilyMemberType.NEUTRAL_DICE) {
            clip.setFill(Service.getColorByFamilyMemberType(familyMemberType));
            clip.setStroke(Service.getColorById(id));
        }
        else {
            clip.setFill(Service.getColorById(id));
            clip.setStroke(Service.getColorByFamilyMemberType(familyMemberType));
        }
        setGraphic(clip);
        setClip(clip);
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> {
            selected = !selected;
            if (selected)
                setEffect(lighting);
            else
                setEffect(null);
            AbstractClient.getInstance().setFamilyMemberType(familyMemberType);
        });
    }
}
