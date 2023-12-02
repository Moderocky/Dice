package mx.kenzie.dice;

import org.junit.Test;

public class DiceTest {
    
    @Test
    public void range() {
        assert Dice.D1.range() == 1;
        assert Dice.D4.range() == 4;
        assert Dice.D6.range() == 6;
        assert Dice.D8.range() == 8;
        assert Dice.D20.range() == 20;
    }
    
    @Test
    public void min() {
        assert new Dice(4, 8).min() == 4;
        assert new Dice(1, 6).min() == 1;
        assert new Dice(6, 1).min() == 6;
    }
    
    @Test
    public void max() {
        assert new Dice(4, 8).max() == 4 * 8;
        assert new Dice(6, 1).max() == 6;
    }
    
    @Test
    public void equals() {
        assert Dice.D20.equals(new Dice(1, 20));
    }
    
}
