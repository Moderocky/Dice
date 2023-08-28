package mx.kenzie.dice;

import java.util.Random;

/**
 * The Rolled interface represents an object that can generate a value, like rolling dice.
 * <p>
 * It's not Rick I promise.
 */
public interface Rolled {

    Random DEFAULT_RANDOM = new Random();

    default int roll() {
        return this.roll(Rolled.DEFAULT_RANDOM);
    }

    default Rolled advantage() {
        return new Advantage(this);
    }

    default Rolled disadvantage() {
        return new Disadvantage(this);
    }

    int roll(Random random);

    int min();

    int max();

    int range();

    record Advantage(Rolled rolled) implements Rolled {

        @Override
        public int roll(Random random) {
            final int x = rolled.roll(random), y;
            if (x == rolled.max()) return x;
            y = rolled.roll(random);
            return Math.max(x, y);
        }

        @Override
        public int min() {
            return rolled.min();
        }

        @Override
        public int max() {
            return rolled.max();
        }

        @Override
        public int range() {
            return rolled.range();
        }

    }

    record Disadvantage(Rolled rolled) implements Rolled {

        @Override
        public int roll(Random random) {
            final int x = rolled.roll(random), y;
            if (x == rolled.min()) return x;
            y = rolled.roll(random);
            return Math.min(x, y);
        }

        @Override
        public int min() {
            return rolled.min();
        }

        @Override
        public int max() {
            return rolled.max();
        }

        @Override
        public int range() {
            return rolled.range();
        }

    }

}
