package application;

import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import control.BoardGrid;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.GameModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Rafał Mikołajun
 */
public class Controller implements Initializable
{
    @FXML
    private BoardGrid board;

    @FXML
    private TextField fieldRefreshRate;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnRand;

    @FXML
    private Button btnClear;

    @FXML
    private Slider fieldBoardSize;

    private GameModel model;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        model = new GameModel(board);
        model.setTimelineDelay(100);
        board.setSize(32);
        fieldRefreshRate.setText("100");
        fieldBoardSize.setValue(board.getSize());
        fieldBoardSize.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                resizeAction(ov, old_val, new_val);
            }
        });
    }

    /**
     * Następna iterakcja gry
     */
    public void nextIterationAction()
    {
        model.oneIteration();
    }

    /**
     * Wylosuj ożywione komórki
     */
    public void randCellAction()
    {
        model.randomLivelySquare(6);
    }

    /**
     * Rozpocznij grę
     */
    public void startAction()
    {
        btnStart.setDisable(true);
        btnStop.setDisable(false);
        btnClear.setDisable(true);
        btnRand.setDisable(true);
        fieldBoardSize.setDisable(true);

        board.setBlocked(true);
        model.getTimeline().play();
    }

    /**
     * Zatrzymaj grę
     */
    public void stopAction()
    {
        btnStart.setDisable(false);
        btnStop.setDisable(true);
        btnClear.setDisable(false);
        btnRand.setDisable(false);
        fieldBoardSize.setDisable(false);

        board.setBlocked(false);
        model.getTimeline().stop();
    }

    /**
     * Czyszczenie planszy
     */
    public void clearAction()
    {
        model.clearBoard();
    }

    /**
     * Zmiana opóźnienia osi czasu
     */
    public void changeRefreshRangeAction()
    {
        Animation.Status status = model.getTimeline().getStatus();
        model.setTimelineDelay(fieldRefreshRate.getText());
        if (status == Animation.Status.RUNNING) {
            model.getTimeline().play();
        }
    }

    /**
     * Zmiana rozmiaru planszy
     */
    public void resizeAction(ObservableValue<? extends Number> ov, Number old_val, Number new_val)
    {
        board.setSize((int)fieldBoardSize.getValue());
    }
}
