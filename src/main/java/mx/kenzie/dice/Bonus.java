package mx.kenzie.dice;

import java.util.Random;

/**
 * Represents a bonus value that can be used in dice roll calculations.
 * Implements the Rolled interface.
 * <p>
 * Specifically used for score generation, such as "2d6+5".
 */
public record Bonus(int value) implements Rolled {

    public Bonus(String counter) {
        this(Integer.parseInt(counter));
    }

    @Override
    public int roll(Random random) {
        return value;
    }

    @Override
    public int roll() {
        return value;
    }

    @Override
    public int min() {
        return value;
    }

    @Override
    public int max() {
        return value;
    }

    @Override
    public int range() {
        return 1;
    }

    public Dice toDice() {
        return new Dice(value, 1);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof final Bonus bonus)) return false;
        return value == bonus.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
