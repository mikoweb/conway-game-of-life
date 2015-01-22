package control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Plansza do gry o strukturze siatki
 * @author Rafał Mikołajun
 */
public class BoardGrid extends GridPane
{
    /**
     * pola na planszy
     */
    private BoardSquare[][] squares;

    /**
     * Rozmiar siatki
     */
    private int size;

    /**
     * blokada planszy
     */
    private boolean blocked;

    /**
     * struktura pola
     */
    public class BoardSquare
    {
        /**
         * Przycisk
         */
        private BoardButton button;

        /**
         * Nr kolumny
         */
        private int col;

        /**
         * Nr wiersza
         */
        private int row;

        public BoardSquare(BoardButton button, int row, int col)
        {
            this.button = button;
            this.row = row;
            this.col = col;
        }

        public int getRow()
        {
            return row;
        }

        public int getCol()
        {
            return col;
        }

        public BoardButton getButton()
        {
            return button;
        }
    }

    /**
     * plansza o rozmiarze jeden
     */
    public BoardGrid()
    {
        this.setSize(1);
        this.blocked = false;
    }

    /**
     * @param size rozmiar planszy
     */
    public BoardGrid(int size)
    {
        this.setSize(size);
        this.blocked = false;
    }

    /**
     * Pobierz rozmiar siatki
     * @return int
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Ustaw rozmiar siatki
     * @param size rozmiar
     */
    public void setSize(int size)
    {
        this.size = size;
        this.squares = new BoardSquare[size][size];
        this.generate();
    }

    /**
     * Kolekcja wszystkich pól
     * @return ArrayList
     */
    public ArrayList<BoardGrid.BoardSquare> getAllSquares()
    {
        ArrayList<BoardGrid.BoardSquare> collection = new ArrayList<BoardGrid.BoardSquare>();

        for (int row = 0; row < this.getSize(); ++row) {
            collection.addAll(Arrays.asList(squares[row]));
        }

        return collection;
    }

    /**
     * Czy istnieje pole o podanych współrzędnych
     * @param row nr wiersza
     * @param col nr kolumny
     */
    public boolean isSquareExists(int row, int col)
    {
        if (row >= 0 && row < this.getSize() && col >= 0 && col < this.getSize()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Walidacja współrzędnych
     * @param row nr wiersza
     * @param col nr kolumny
     */
    private void cellPositionValidation(int row, int col)
    {
        if (row < 0 || row >= this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        }

        if (col < 0 || col >= this.getSize()) {
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        }
    }

    /**
     * Ustaw pole o podanych współrzędnych
     * @param row nr wiersza
     * @param col nr kolumny
     * @param button pole
     */
    public void setSquare(int row, int col, BoardButton button)
    {
        this.cellPositionValidation(row, col);
        this.squares[row][col] = new BoardSquare(button, row, col);
        this.add(button, col, row);
    }

    /**
     * Pobierz pole o podanych współrzędnych
     * @param row nr wiersza
     * @param col nr kolumny
     * @return BoardGrid.BoardSquare
     */
    public BoardGrid.BoardSquare getSquare(int row, int col)
    {
        this.cellPositionValidation(row, col);
        return this.squares[row][col];
    }

    /**
     * Generowanie planszy.
     */
    private void generate()
    {
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        for (int i = 0; i < this.getSize(); ++i) {
            this.getColumnConstraints().add(new ColumnConstraints());
            this.getRowConstraints().add(new RowConstraints());
        }

        for (int row = 0; row < this.getSize(); ++row) {
            for (int col = 0; col < this.getSize(); ++col) {
                final BoardButton button = new BoardButton();
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!isBlocked()) {
                            if (button.isActive()) {
                                button.setState(BoardButton.STATE_INACTIVE);
                            } else {
                                button.setState(BoardButton.STATE_ACTIVE);
                            }
                        }
                    }
                });

                this.setSquare(row, col, button);
            }
        }
    }

    /**
     * Zwraca kolekcję pól sąsiadujących z polem o podanych współprzędnych
     * @param row nr wiersza
     * @param col nr kolumny
     * @return ArrayList<BoardSquare>
     */
    public ArrayList<BoardGrid.BoardSquare> getSquareNeighbors(int row, int col)
    {
        this.cellPositionValidation(row, col);
        ArrayList<BoardGrid.BoardSquare> list = new ArrayList<BoardGrid.BoardSquare>();

        for (int i = col - 1; i < col + 2; ++i) {
            if (this.isSquareExists(row - 1, i)) {
                list.add(this.getSquare(row - 1, i));
            }

            if (this.isSquareExists(row + 1, i)) {
                list.add(this.getSquare(row + 1, i));
            }
        }

        if (this.isSquareExists(row, col - 1)) {
            list.add(this.getSquare(row, col - 1));
        }

        if (this.isSquareExists(row, col + 1)) {
            list.add(this.getSquare(row, col + 1));
        }

        return list;
    }

    /**
     * czy zablokowana
     * @return boolean
     */
    public boolean isBlocked()
    {
        return blocked;
    }

    /**
     * ustaw blokadę
     * @param blocked true|false
     */
    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }
}
