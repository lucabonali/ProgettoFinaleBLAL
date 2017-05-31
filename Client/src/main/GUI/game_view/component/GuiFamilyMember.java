package main.GUI.game_view.component;

import javafx.scene.Cursor;
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
public class GuiFamilyMember extends Circle {
    private static final double RADIUS = 15;
    private int id;
    private Color color;
    private FamilyMemberType familyMemberType;
    private Circle circle;
    private Rectangle rectangle;
    private boolean selected = false;

    public GuiFamilyMember(int id, FamilyMemberType familyMemberType) {
        super(RADIUS);
        this.id = id;
        this.familyMemberType = familyMemberType;
        createPane();
    }

    private void createPane() {
        setStyle("-fx-border-color: black");
        setStrokeWidth(6);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        if (familyMemberType == FamilyMemberType.NEUTRAL_DICE) {
            setFill(Service.getColorByFamilyMemberType(familyMemberType));
            setStroke(Service.getColorById(id));
        }
        else {
            setFill(Service.getColorById(id));
            setStroke(Service.getColorByFamilyMemberType(familyMemberType));
        }
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
