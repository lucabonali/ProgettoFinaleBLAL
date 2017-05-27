package main.gui.game_view.component;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.api.types.ActionSpacesType;

/**
 * @author Luca
 * @author Andrea
 */
public class SingleActionSpace extends Pane implements ActionSpaceInterface{
    private static final double WIDTH = 64, HEIGHT = 38;
    private GraphicFamilyMember familyMember;
    private ActionSpacesType type;
    private Rectangle rectangle;

    public SingleActionSpace(ActionSpacesType type) {
        this.type = type;
        setMaxSize(WIDTH, HEIGHT);
        createPane();
        setOnMouseClicked(event -> {
            GraphicFamilyMember fm = new GraphicFamilyMember(1);
            addFamilyMember(fm);
        });
    }

    private void createPane() {
        rectangle = new Rectangle(WIDTH, HEIGHT);
        rectangle.setArcHeight(HEIGHT/2);
        rectangle.setArcWidth(WIDTH/2);
        rectangle.fillProperty().bind(
                Bindings.when(hoverProperty())
                .then(Color.color(1,1,1, 0.75))
                .otherwise(Color.color(1,1,1,0.25))
        );
        rectangle.setCursor(Cursor.HAND);
        getChildren().add(rectangle);
    }

    @Override
    public void addFamilyMember(GraphicFamilyMember familyMember) {
        this.familyMember = familyMember;
        familyMember.setTranslateX(30);
        familyMember.setTranslateY(18);
        getChildren().add(familyMember);
    }

    @Override
    public void removeAllFamilyMembers() {
        getChildren().remove(familyMember);
        familyMember = null;
    }

    @Override
    public ActionSpacesType getType() {
        return type;
    }
}
