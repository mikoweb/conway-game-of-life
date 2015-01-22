package model;

import control.BoardButton;
import control.BoardGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Model gry w życie
 * @author Rafał Mikołajun
 */
public class GameModel
{
    /**
     * plansza do gry
     */
    private BoardGrid board;

    /**
     * oś czasu
     */
    private Timeline timeline;

    /**
     * @param board plansza
     */
    public GameModel(BoardGrid board)
    {
        this.board = board;
        timeline = new Timeline();
        setTimelineDelay(10);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Pobierz planszę
     * @return BoardGrid
     */
    public BoardGrid getBoard()
    {
        return board;
    }

    /**
     * Ustaw planszę
     * @param board plansza
     */
    public void setBoard(BoardGrid board)
    {
        this.board = board;
    }

    /**
     * Jedna iteracja gry
     */
    public void oneIteration()
    {
        for (BoardGrid.BoardSquare square: board.getAllSquares()) {
            ArrayList<BoardGrid.BoardSquare> activeNeighbors = this.getActiveSquaresFromCollection(
                    board.getSquareNeighbors(square.getRow(), square.getCol())
            );
            BoardButton button = square.getButton();

            if (button.isActive()) {
                if (activeNeighbors.size() > 3) {
                    // żywa komórka, która posiada więcej niż 3 sąsiadów umiera z przeludnienia
                    button.setState(BoardButton.STATE_INACTIVE);
                } else if (activeNeighbors.size() < 2) {
                    // żywa komórka, która posiada mniej niż 2 sąsiadów umiera z nadmiernej izolacji
                    button.setState(BoardButton.STATE_INACTIVE);
                }
            } else if (activeNeighbors.size() == 3) {
                // komórka, która jest martwa i posiada 3 sąsiadów zostaje ożywiona
                button.setState(BoardButton.STATE_ACTIVE);
            }
        }
    }

    /**
     * Pobierz aktywne pola z kolekcji
     * @param squares kolekcja pól
     * @return ArrayList<BoardGrid.BoardSquare>
     */
    public ArrayList<BoardGrid.BoardSquare> getActiveSquaresFromCollection(ArrayList<BoardGrid.BoardSquare> squares)
    {
        ArrayList<BoardGrid.BoardSquare> collection = new ArrayList<BoardGrid.BoardSquare>();

        for (BoardGrid.BoardSquare square: squares) {
            if (square.getButton().isActive()) {
                collection.add(square);
            }
        }

        return collection;
    }

    /**
     * Losowanie ożywionych komórek
     * @param range zakres
     */
    public void randomLivelySquare(int range)
    {
        Random rand = new Random();
        for (BoardGrid.BoardSquare square: board.getAllSquares()) {
            if (0 == rand.nextInt(range)) {
                square.getButton().setState(BoardButton.STATE_ACTIVE);
            } else {
                square.getButton().setState(BoardButton.STATE_INACTIVE);
            }
        }
    }

    /**
     * Wyczyść planszę
     */
    public void clearBoard()
    {
        board.setSize(board.getSize());
    }

    /**
     * Ustaw opóźnienie osi czasu
     * @param delay interwał czasowy
     */
    public void setTimelineDelay(String delay)
    {
        if (!delay.matches("[0-9]+") || delay.isEmpty()) {
            this.setTimelineDelay(50);
        } else {
            this.setTimelineDelay(Integer.parseInt(delay));
        }
    }

    /**
     * Ustaw opóźnienie osi czasu
     * @param delay interwał czasowy
     */
    public void setTimelineDelay(int delay)
    {
        getTimeline().stop();
        getTimeline().getKeyFrames().clear();
        if (delay < 10) {
            getTimeline().getKeyFrames().add(createKeyFrame(10));
        } else {
            getTimeline().getKeyFrames().add(createKeyFrame(delay));
        }
    }

    /**
     * utwórz klatkę animacji
     * @param delay interwał czasowy
     * @return KeyFrame
     */
    private KeyFrame createKeyFrame(int delay)
    {
        return new KeyFrame(Duration.millis(delay), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneIteration();
            }
        });
    }

    /**
     * Dostęp do osi czasu
     * @return Timeline
     */
    public Timeline getTimeline()
    {
        return timeline;
    }
}
