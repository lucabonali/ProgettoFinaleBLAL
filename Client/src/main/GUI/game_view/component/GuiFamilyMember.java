package main.GUI.game_view.component;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.api.types.FamilyMemberType;
import main.client.AbstractClient;
import main.GUI.Service;

/**
 * @author Luca
 * @author Andrea
 */
public class GuiFamilyMember extends Pane {
    private static final double RADIUS = 15;
    private int id;
    private Color color;
    private FamilyMemberType familyMemberType;
    private Circle circle;
    private Rectangle rectangle;

    public GuiFamilyMember(int id, FamilyMemberType familyMemberType) {
        this.id = id;
        this.familyMemberType = familyMemberType;
        setPrefSize(RADIUS, RADIUS);
        createPane();
    }

    private void createPane() {
        circle = new Circle(RADIUS, Service.getColorById(id));
        circle.setCenterX(RADIUS/2);
        circle.setCenterY(RADIUS/2);
        circle.setCursor(Cursor.HAND);
        getChildren().add(circle);
        rectangle = new Rectangle();
        rectangle.setCursor(Cursor.HAND);
        rectangle.setX((RADIUS/2) - 4);
        rectangle.setY((RADIUS/2) - 4);
        rectangle.setWidth(8);
        rectangle.setHeight(8);
        rectangle.setFill(Service.getColorByFamilyMemberType(familyMemberType));
        getChildren().add(rectangle);
        setOnMouseClicked(event -> AbstractClient.getInstance().setFamilyMemberType(familyMemberType));
    }
}
