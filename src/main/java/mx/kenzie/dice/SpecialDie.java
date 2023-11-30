package mx.kenzie.dice;

import java.util.Objects;
import java.util.Random;

/**
 * A special die that uses an abstract number rather than the dice int.
 */
public record SpecialDie(Number rolls, Number sides) implements Rolled {
    
    @Override
    public int approximateRoll() {
        return Rolled.DEFAULT_RANDOM.nextInt(rolls.intValue(), sides.intValue() * rolls.intValue() + 1);
    }
    
    @Override
    public int roll(Random random) {
        return random.nextInt(rolls.intValue(), sides.intValue() * rolls.intValue() + 1);
    }
    
    @Override
    public int min() {
        return rolls.intValue();
    }
    
    @Override
    public int max() {
        return rolls.intValue() * sides.intValue();
    }
    
    @Override
    public int range() {
        return 1 + (rolls.intValue() * sides.intValue()) - rolls.intValue();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialDie dice)) return false;
        return Objects.equals(rolls, dice.rolls) && Objects.equals(sides, dice.sides);
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
