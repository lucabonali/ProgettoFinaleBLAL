package main.gui.game_view.component;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.api.types.ActionSpacesType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lampa
 */
public class CouncilActionSpace extends Pane implements ActionSpaceInterface {
    private static final int WIDTH = 218, HEIGHT = 55;
    private int counter;
    private List<GraphicFamilyMember> familyMemberList;
    private ActionSpacesType type;
    private Rectangle rectangle;

    public CouncilActionSpace(ActionSpacesType type) {
        this.type = type;
        familyMemberList = new ArrayList<>();
        setMaxSize(WIDTH, HEIGHT);
        createPane();
    }

    private void createPane() {
        rectangle = new Rectangle(WIDTH, HEIGHT);
        rectangle.setArcHeight(HEIGHT/3);
        rectangle.setArcWidth(WIDTH/4);
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
        familyMemberList.add(familyMember);
        familyMember.setTranslateX(20 + (30*counter));
        familyMember.setTranslateY(27);
        getChildren().add(familyMember);
        counter++;
    }


    @Override
    public void removeAllFamilyMembers() {
        familyMemberList.forEach(familyMember -> getChildren().remove(familyMember));
        familyMemberList = new ArrayList<>();
    }

    @Override
    public ActionSpacesType getType() {
        return type;
    }
}
