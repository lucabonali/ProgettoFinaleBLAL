package main.GUI.game_view.component;

import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private FamilyMemberType familyMemberType;

    public GuiFamilyMember(int id, FamilyMemberType familyMemberType) {
        super();
        this.id = id;
        this.familyMemberType = familyMemberType;
        setPrefSize(RADIUS*2, RADIUS*2);
        createPane();
    }

    private void createPane() {
        Circle circle = new Circle(RADIUS);
        circle.setStrokeWidth(6);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        if (familyMemberType == FamilyMemberType.NEUTRAL_DICE) {
            circle.setFill(Service.getColorByFamilyMemberType(familyMemberType));
            circle.setStroke(Service.getColorById(id));
        }
        else {
            circle.setFill(Service.getColorById(id));
            circle.setStroke(Service.getColorByFamilyMemberType(familyMemberType));
        }
        setShape(circle);
        setGraphic(circle);
        setCursor(Cursor.HAND);
        setEffect(new DropShadow(RADIUS, Color.BLACK));
        addMouseClicked();
    }

    public int getFamilyId() {
        return id;
    }

    public void addMouseClicked() {
        FamilyMemberType type = familyMemberType;
        setOnMouseClicked(event -> AbstractClient.getInstance().setFamilyMemberType(type));
    }

    public void removeMouseClicked() {
        setOnMouseClicked(null);
    }
}
