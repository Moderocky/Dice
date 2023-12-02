package mx.kenzie.dice;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Random;

/**
 * The Rolled interface represents an object that can generate a value, like rolling dice.
 * <p>
 * It's not Rick I promise.
 */
public interface Rolled extends Comparable<Rolled>, Serializable {
    
    Random DEFAULT_RANDOM = new Random();
    
    /**
     * Compares this Rolled object with another Rolled object.
     *
     * @param o the object to be compared
     * @return 0 if both objects have the same minimum and maximum values,
     * 1 if this object has greater minimum and maximum values,
     * -1 if this object has smaller minimum and maximum values,
     * a comparison of the average value of the rolls of each object.
     */
    @Override
    default int compareTo(@NotNull Rolled o) {
        if (this.min() == o.min() && this.max() == o.max()) return 0;
        if (this.min() >= o.min() && this.max() >= o.max()) return 1;
        if (this.min() <= o.min() && this.max() <= o.max()) return -1;
        return Integer.compare(this.min() + (this.range() / 2), o.min() + (o.range() / 2));
    }
    
    default int roll() {
        return this.roll(Rolled.DEFAULT_RANDOM);
    }
    
    /**
     * A faster rolling method that produces a value between the minimum and maximum, ignoring biases.
     * Generates a random number between the min and max values (inclusive)
     * using the default random source.
     *
     * @return An evenly-distributed random roll between the min and max values.
     */
    default int approximateRoll() {
        return DEFAULT_RANDOM.nextInt(this.min(), this.max() + 1);
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
