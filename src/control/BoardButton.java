package control;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

/**
 * Przycisk na planszy
 * @author Rafał Mikołajun
 */
public class BoardButton extends Button
{
    /**
     * Nieaktywny
     */
    public static final byte STATE_INACTIVE = 0;

    /**
     * Aktywny
     */
    public static final byte STATE_ACTIVE = 1;

    /**
     * Stan przycisku
     */
    private byte state;

    /**
     * Domyślna klass CSS
     */
    private String styleClassDefault;

    /**
     * Klasa CSS dla stanu STATE_ACTIVE
     */
    private String styleClassActive;

    /**
     * Konstruktor bezargumentowy.
     * Domyślny stan STATE_INACTIVE
     */
    public BoardButton()
    {
        super();
        this.setStyleClassDefault("btn-board");
        this.setStyleClassActive("active");
        this.setState(STATE_INACTIVE);
    }

    /**
     * @param styleClassDefault domyślna nazwa klasy CSS
     * @param styleClassActive nazwa klasy CSS dla stanu STATE_ACTIVE
     */
    public BoardButton(String styleClassDefault, String styleClassActive)
    {
        super();
        this.setStyleClassDefault(styleClassDefault);
        this.setStyleClassActive(styleClassActive);
        this.setState(STATE_INACTIVE);
    }

    /**
     * Odświeżanie klasy CSS
     */
    public void refreshStyleClass()
    {
        ObservableList<String> styleClass = this.getStyleClass();
        styleClass.remove("button");

        switch (this.getState()) {
            case STATE_ACTIVE:
                styleClass.addAll(this.getStyleClassDefault(), this.getStyleClassActive());
                break;
            case STATE_INACTIVE:
            default:
                styleClass.add(this.getStyleClassDefault());
                styleClass.remove(this.getStyleClassActive());
                break;
        }
    }

    /**
     * Pobierz stan przycisku
     * @return byte
     */
    public byte getState()
    {
        return state;
    }

    /**
     * Ustaw stan przycisku
     * @param state stan przycisku
     */
    public void setState(byte state)
    {
        if (state < STATE_INACTIVE || state > STATE_ACTIVE) {
            throw new IllegalArgumentException("unknown state");
        }

        this.state = state;
        this.refreshStyleClass();
    }

    /**
     * Czy przycisk jest aktywny
     * @return bool
     */
    public boolean isActive()
    {
        return this.state == STATE_ACTIVE;
    }

    /**
     * Pobierz domyślną klasę CSS
     * @return string
     */
    public String getStyleClassDefault()
    {
        return styleClassDefault;
    }

    /**
     * Ustaw domyślną kasę CSS
     * @param styleClassDefault nazwa klasy
     */
    public void setStyleClassDefault(String styleClassDefault)
    {
        this.styleClassDefault = styleClassDefault;
    }

    /**
     * Pobierz klasę CSS dla przycisku aktywnego
     * @return string
     */
    public String getStyleClassActive()
    {
        return styleClassActive;
    }

    /**
     * Ustaw kasę CSS dla przycisku aktywnego
     * @param styleClassActive nazwa klasy
     */
    public void setStyleClassActive(String styleClassActive)
    {
        this.styleClassActive = styleClassActive;
    }
}
