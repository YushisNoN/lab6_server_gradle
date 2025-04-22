package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Comparable<Location>, Serializable {
    @JsonProperty("x")
    private long x;

    @NotNull(message = "Высота не может отсутствовать")
    @JsonProperty("y")
    private Double y; // Поле не может быть null

    @JsonProperty("z")
    private double z;

    public Location() {
    }

    public void setY(Double newHeight) throws NullValueException {
        if (null == newHeight) {
            throw new NullValueException();
        }
        this.y = newHeight;
    }

    public void setX(long newWidth) {
        this.x = newWidth;
    }

    public void setZ(double newDepth) {
        this.z = newDepth;
    }

    public long getX() {
        return this.x;
    }

    public double getZ() {
        return this.z;
    }

    public Double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "Location={" +
                "width=" + this.x +
                ", height=" + this.y +
                ", depth=" + this.z +
                "}";
    }

    @Override
    public int compareTo(Location other) {
        int xComparison = Long.compare(this.x, other.x);
        if (xComparison != 0) {
            return xComparison;
        }
        int yComparison = this.y.compareTo(other.y);
        if (yComparison != 0) {
            return yComparison;
        }
        return Double.compare(this.z, other.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Location myClass = (Location) o;
        return x == myClass.x &&
                Double.compare(myClass.z, z) == 0 &&
                y.equals(myClass.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}