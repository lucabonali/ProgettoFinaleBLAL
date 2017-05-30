package main.GUI.game_mode_selection;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import main.GUI.game_view.GUIController;
import main.GUI.music.Music;
import main.GUILauncher;
import main.client.AbstractClient;



import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Luca
 * @author Andrea
 */
public class GameModeSelectionView {
    private static final int RANDOM = 1;
    private static final int TWO_PLAYERS = 2;
    private static final int THREE_PLAYERS = 3;
    private static final int FOUR_PLAYERS = 4;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 620;


    private DoubleProperty xTitleProperty, yTitleProperty, xLineProperty, yLineProperty, endYLineProperty;

    //lista di coppie dei miei Item del menu di partenza
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Random", new RunGameMode(RANDOM)),
            new Pair<String, Runnable>("Two Players", new RunGameMode(TWO_PLAYERS)),
            new Pair<String, Runnable>("Three Players", new RunGameMode(THREE_PLAYERS)),
            new Pair<String, Runnable>("Four Players", new RunGameMode(FOUR_PLAYERS)),
            new Pair<String, Runnable>("Game Options", () -> {}),
            new Pair<String, Runnable>("Credits", () -> {}),
            new Pair<String, Runnable>("Exit to Desktop", () -> {System.exit(0);})
    );

    private Pane root;
    private VBox menuBox ;
    private Line line;

    private Music theme;

    public GameModeSelectionView() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        root = new Pane();
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        menuBox = new VBox(-5);
        xTitleProperty = new SimpleDoubleProperty();
        yTitleProperty = new SimpleDoubleProperty();
        xLineProperty = new SimpleDoubleProperty();
        yLineProperty = new SimpleDoubleProperty();
        endYLineProperty = new SimpleDoubleProperty();
        theme = new Music();
        theme.play(theme.getPath()+"MainTheme.wav");
    }

    public Parent createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 5 + 50;
        addLine(lineX, lineY);

        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    /**
     * aggiungo il titolo al mio root
     */
    private void addTitle() {
        GameModeTitle title = new GameModeTitle("LORENZO IL MAGNIFICO");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 5);
        title.translateXProperty().bind(xTitleProperty);
        title.translateYProperty().bind(yTitleProperty);

        root.widthProperty().addListener((observableValue, oldRootWidth, newRootWidth) -> {
            double xTitle = (newRootWidth.doubleValue()/2) - (title.getTitleWidth()/2);
            xTitleProperty.setValue(xTitle);
        });
        root.heightProperty().addListener((observableValue, oldRootHeight, newRootHeight) -> {
            double yTitle = newRootHeight.doubleValue()/5;
            yTitleProperty.setValue(yTitle);

        });

        root.getChildren().add(title);
    }

    /**
     * aggiungo lo sfondo
     */
    private void addBackground() {
        ImageView imageView = new ImageView(new Image(getClass().getResource("res/old_florence.jpg").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().add(imageView);
    }

    /**
     * aggiungo il menu
     * @param x
     * @param y
     */
    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y+20);
        menuData.forEach(data -> {
            GameModeItem item = new GameModeItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }

    /**
     * mi aggiunge la line sulla quale verrà appoggiato il menu
     * @param x
     * @param y
     */
    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 300);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(2), line);
        st.setToY(0.9);
        st.setOnFinished(e -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    public static void createGameModeSelectionView() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        GameModeSelectionView nextView = new GameModeSelectionView();
        Parent window = nextView.createContent();
        Platform.runLater(()->{
            Stage stage = GUILauncher.getPrimaryStage();
            Scene scene = new Scene(window, 1000, 620);
            stage.setScene(scene);
            stage.setTitle("GAME SELECTION");
            stage.centerOnScreen();
        });
    }


    private class RunGameMode implements Runnable{
        private AbstractClient client;
        private int gameMode;

        public RunGameMode(int gameMode) {
            this.gameMode = gameMode;
            this.client = AbstractClient.getInstance();
        }


        @Override
        public void run() {
            try {
                client.startGame(gameMode);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/gui/game_view/game_view.fxml"));
                Parent window = (Pane) fxmlLoader.load();
                GUIController controller = fxmlLoader.getController();
                AbstractClient.getInstance().setInterfaceController(controller); //setto il model
                Platform.runLater(()->{
                    theme.stop();
                    Stage stage = (Stage) root.getScene().getWindow();
                    Scene scene = new Scene(window);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    //stage.sizeToScene();
                    stage.setTitle("LORENZO IL MAGNIFICO!");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
