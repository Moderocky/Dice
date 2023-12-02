package mx.kenzie.dice;

import org.junit.Test;

import java.util.Random;

public class RolledTest {
    
    public static void checkAdvantageAverage() {
        //<editor-fold desc="Print" defaultstate="collapsed">
        final Random random = Rolled.DEFAULT_RANDOM;
        final int rolls = 1000000;
        final Dice dice = new Dice(1, 20);
        {
            long count = 0;
            for (int i = 0; i < rolls; i++) {
                count += dice.roll(random);
            }
            final double total = (double) count / rolls;
            System.out.println("Regular average: " + total);
        }
        {
            final Rolled rolled = dice.disadvantage();
            long count = 0;
            for (int i = 0; i < rolls; i++) {
                count += rolled.roll(random);
            }
            final double total = (double) count / rolls;
            System.out.println("Disadvantaged average: " + total);
        }
        {
            final Rolled rolled = dice.advantage();
            long count = 0;
            for (int i = 0; i < rolls; i++) {
                count += rolled.roll(random);
            }
            final double total = (double) count / rolls;
            System.out.println("Advantaged average: " + total);
        }
        {
            final Rolled rolled = dice.disadvantage().advantage();
            long count = 0;
            for (int i = 0; i < rolls; i++) {
                count += rolled.roll(random);
            }
            final double total = (double) count / rolls;
            System.out.println("Advantaged disadvantaged average: " + total);
        }
        {
            final Rolled rolled = dice.advantage().disadvantage();
            long count = 0;
            for (int i = 0; i < rolls; i++) {
                count += rolled.roll(random);
            }
            final double total = (double) count / rolls;
            System.out.println("Disadvantaged advantaged average: " + total);
        }
        //</editor-fold>
    }
    
    @Test
    public void advantage() {
        final Dice dice = Dice.D20;
        final Rolled rolled = dice.advantage();
        for (int i = 0; i < 100; i++) {
            assert rolled.roll() >= 1;
            assert rolled.roll() <= 20;
        }
    }
    
    @Test
    public void disadvantage() {
        final Dice dice = Dice.D20;
        final Rolled rolled = dice.disadvantage();
        for (int i = 0; i < 100; i++) {
            assert rolled.roll() >= 1;
            assert rolled.roll() <= 20;
        }
    }
    
}
