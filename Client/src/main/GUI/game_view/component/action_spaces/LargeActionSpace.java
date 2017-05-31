package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.GUI.game_view.component.GuiFamilyMember;
import main.api.types.ActionSpacesType;
import main.client.AbstractClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 */
public class LargeActionSpace extends Pane implements ActionSpaceInterface{
    private static final int WIDTH = 185, HEIGHT = 39;
    private int counter;
    private List<GuiFamilyMember> familyMemberList;
    private ActionSpacesType type;
    private Rectangle rectangle;
    private GridPane container;

    public LargeActionSpace(ActionSpacesType type, GridPane container) {
        this.type = type;
        this.container = container;
        familyMemberList = new ArrayList<>();
        setMaxSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> setCurrentActionSpace());
    }


    public GridPane getContainer() {
        return container;
    }

    public void setFamilyMember(GuiFamilyMember familyMember) {
        familyMemberList.add(familyMember);
    }

    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        familyMemberList.add(familyMember);
        familyMember.setTranslateX(25 + (25*counter));
        familyMember.setTranslateY(19.5);
        Platform.runLater(() -> container.getChildren().add(familyMember));
        counter++;
    }

    @Override
    public void removeAllFamilyMembers() {
        Platform.runLater(() -> container.getChildren().removeAll(familyMemberList));
        //Platform.runLater(() -> familyMemberList.forEach(familyMember -> container.getChildren().remove(familyMember)));
        familyMemberList = new ArrayList<>();
    }

    @Override
    public ActionSpacesType getType() {
        return type;
    }

    @Override
    public void setCurrentActionSpace() {
        AbstractClient.getInstance().setActionSpacesType(type);
    }
}
