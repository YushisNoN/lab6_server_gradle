package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {

    @NotNull(message = "Не может быть переменная Х быть null")
    @Min(value = -851, message = "Не может быть меньше -851 значение Х")
    @JsonProperty("x")
    private Long x; // Значение поля должно быть больше -852, Поле не может быть null

    @NotNull(message = "Не может быть переменная Y быть null")
    @JsonProperty("y")
    private Integer y; // Поле не может быть null

    public void setX(Long newValueX) throws NullValueException, CoordinateWrongValueException {
        if (null == newValueX) {
            throw new NullValueException();
        }
        if (newValueX <= -852) {
            throw new CoordinateWrongValueException();
        }
        this.x = newValueX;
    }

    public void setY(Integer newValueY) throws NullValueException {
        if (null == newValueY) {
            throw new NullValueException();
        }
        this.y = newValueY;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + this.x +
                ", y=" + this.y +
                "}";
    }

    @Override
    public int compareTo(Coordinates other) {
        int xComparison = this.x.compareTo(other.x);
        if (xComparison != 0) {
            return xComparison;
        }
        return this.y.compareTo(other.y);
    }
}