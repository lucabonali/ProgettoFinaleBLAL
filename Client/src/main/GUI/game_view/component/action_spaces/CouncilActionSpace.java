package main.GUI.game_view.component.action_spaces;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import main.GUI.game_view.GUIController;
import main.GUI.game_view.component.GuiFamilyMember;
import main.api.types.ActionSpacesType;

/**
 * @author lampa
 */
public class CouncilActionSpace extends LargeActionSpace{
    private static final int WIDTH = 240, HEIGHT = 55;
    private int counter;

    public CouncilActionSpace(ActionSpacesType type, GridPane container, GUIController guiController) {
        super(type, container, guiController);
        setMaxSize(WIDTH, HEIGHT);
        setPrefSize(WIDTH, HEIGHT);
        setOnMouseClicked(event -> setCurrentActionSpace());
    }

    @Override
    public void addFamilyMember(GuiFamilyMember familyMember) {
        setFamilyMember(familyMember);
        familyMember.setTranslateX(20 + (30*counter));
        familyMember.setTranslateY(27);
        Platform.runLater(() -> getContainer().getChildren().add(familyMember));
        counter++;
    }
}
