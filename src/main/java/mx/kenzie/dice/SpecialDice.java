package mx.kenzie.dice;

import java.io.Serial;
import java.util.Objects;
import java.util.Random;

/**
 * A special die that uses an abstract number rather than the dice int.
 */
public class SpecialDice extends Dice implements Rolled {
    
    @Serial
    private static final long serialVersionUID = 0L;
    private final Number rolls;
    private final Number sides;
    
    public SpecialDice(Number rolls, Number sides) {
        super(0, 0);
        this.rolls = rolls;
        this.sides = sides;
    }
    
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
        if (!(o instanceof SpecialDice dice)) return false;
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
    
    @Override
    public int rolls() {
        return rolls.intValue();
    }
    
    @Override
    public int sides() {
        return sides.intValue();
    }
    
    
}
