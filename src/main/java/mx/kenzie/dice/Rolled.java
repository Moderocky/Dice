package mx.kenzie.dice;

import java.util.Random;

/**
 * The Rolled interface represents an object that can generate a value, like rolling dice.
 * <p>
 * It's not Rick I promise.
 */
public interface Rolled {

    Random DEFAULT_RANDOM = new Random();

    static Reader read(String text, final int start) {
        if (text == null) return new Reader(null, start);
        final int length = text.length();
        if (length == 0 || start >= length) return new Reader(null, start);
        if (text.substring(start).isBlank()) return new Reader(null, start);
        int pointer = start;
        boolean d = false, bump = false;
        end:
        while (pointer < length) {
            final char c = text.charAt(pointer);
            switch (c) {
                case 'd':
                    d = true;
                    break;
                case '+':
                    bump = true;
                    break end;
                case '-':
                    if (pointer == start) break;
                    if (text.substring(start, pointer - 1).isBlank()) break;
                    break end;
            }
            pointer++;
        }
        final String part = text.substring(start, pointer);
        if (bump) pointer++;
        if (d) return new Reader(new Dice(part.trim()), pointer);
        else return new Reader(new Bonus(part.trim()), pointer);

    }

    default int roll() {
        return this.roll(Rolled.DEFAULT_RANDOM);
    }

    int roll(Random random);

    int min();

    int max();

    int range();

    record Reader(Rolled rolled, int pointer) {

        public boolean success() {
            return rolled != null;
        }

    }

}
