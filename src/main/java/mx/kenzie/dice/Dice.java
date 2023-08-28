package mx.kenzie.dice;

import java.util.Objects;
import java.util.Random;

/**
 * Represents an N-sided die.
 * Contains a strict instruction for how many times this die should be rolled.
 * Implements the Rolled interface to provide methods for rolling the dice.
 */
public record Dice(int rolls, int sides) implements Rolled {

    public static final Dice D1 = new Dice(1, 1),
        D4 = new Dice(1, 4),
        D8 = new Dice(1, 8),
        D6 = new Dice(1, 6), D20 = new Dice(1, 20);

    public Dice(String text) {
        this(Dice.unpack(text));
    }

    private Dice(String[] parts) {
        this(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    private Dice(int[] ints) {
        this(ints[0], ints[1]);
    }

    private static int[] unpack(String counter) {
        final int index = counter.indexOf('d');
        return new int[]{
            Integer.parseInt(counter.substring(0, index).trim()),
            Integer.parseInt(counter.substring(index + 1).trim())
        };
    }

    public static Dice of(String counter) {
        final int index = counter.indexOf('d');
        return new Dice(
            Integer.parseInt(counter.substring(0, index).trim()),
            Integer.parseInt(counter.substring(index + 1).trim())
        );
    }

    @Override
    public int roll(Random random) {
        int total = 0;
        for (int i = 0; i < rolls; i++) total += random.nextInt(0, sides) + 1;
        return total;
    }

    @Override
    public int min() {
        return rolls;
    }

    @Override
    public int max() {
        return rolls * sides;
    }

    @Override
    public int range() {
        return 1 + (rolls * sides) - rolls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dice dice)) return false;
        return rolls == dice.rolls && sides == dice.sides;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolls, sides);
    }

    @Override
    public String toString() {
        return rolls + "d" + sides;
    }

}