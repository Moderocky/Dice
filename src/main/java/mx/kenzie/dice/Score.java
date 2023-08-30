package mx.kenzie.dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public record Score(int min, int max, Rolled... values) implements Rolled {

    public Score(Rolled... values) {
        this(Score.min(values), Score.max(values), values);
    }

    public Score(String counter) {
        this(Score.values(counter));
    }

    public static Rolled[] values(String counter) {
        final List<Rolled> list = new ArrayList<>(8);
        int start = 0;
        do {
            final Reader reader = Score.read(counter, start);
            if (!reader.success()) break;
            list.add(reader.rolled());
            start = reader.pointer();
        } while (true);
        return list.toArray(new Rolled[0]);
    }

    public static Score of(String counter) {
        return new Score(Score.values(counter));
    }

    private static Reader read(String text, final int start) {
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

    private static int min(Rolled... values) {
        int value = 0;
        for (final Rolled rolled : values) value += rolled.min();
        return value;
    }

    public static int max(Rolled... values) {
        int value = 0;
        for (final Rolled rolled : values) value += rolled.max();
        return value;
    }

    public Rolled simplify() {
        if (this.values.length < 2) return this;
        final int min = this.min(), max = this.max();
        final int range = 1 + max - min;
        if (min == 1) return new Dice(1, range);
        if ((range + (min - 1)) % min == 0) return new Dice(min, (range + (min - 1)) / min);
        final Dice dice = new Dice(1, range);
        return new Score(dice, new Bonus(min - 1));
    }

    @Override
    public int roll() {
        int value = 0;
        for (final Rolled rolled : values) value += rolled.roll();
        return value;
    }

    @Override
    public int roll(Random random) {
        int value = 0;
        for (final Rolled rolled : values) value += rolled.roll(random);
        return value;
    }

    @Override
    public int range() {
        return 1 + this.max() - this.min();
    }

    @Override
    public String toString() {
        if (values.length == 0) return "0";
        final StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final Rolled value : values) {
            if (first) first = false;
            else {
                if (value instanceof Bonus bonus) {
                    if (bonus.value() > 0) builder.append('+');
                    else if (bonus.value() == 0) continue;
                    else builder.append('-');
                } else builder.append("+");
            }
            builder.append(value.toString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof final Rolled score)) return false;
        return score.min() == this.min() && score.max() == this.max();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    protected record Reader(Rolled rolled, int pointer) {

        public boolean success() {
            return rolled != null;
        }

    }

}
