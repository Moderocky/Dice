package mx.kenzie.dice;

import org.junit.Test;

public class ScoreTest {

    @Test
    public void roll() {
        final Score score = new Score("3d6+2");
        for (int i = 0; i < 1000; i++) {
            assert score.roll() >= (2 + 3);
            assert score.roll() <= (2 + 3 * 6);
        }
    }

    @Test
    public void min() {
        final Score score = new Score("2d6-1");
        for (int i = 0; i < 1000; i++) assert score.roll() >= 1;
    }

    @Test
    public void max() {
        final Score score = new Score("1d6+1d20");
        for (int i = 0; i < 1000; i++) assert score.roll() <= (6 + 20);
    }

    @Test
    public void range() {
        assert new Score("1d6+1d20").range() == 25;
        for (int i = 0; i < 1000; i++) {
            final int a = Rolled.DEFAULT_RANDOM.nextInt(1, 50), b = Rolled.DEFAULT_RANDOM.nextInt(1, 50);
            final Dice first = new Dice(1, a), second = new Dice(1, b);
            assert first.range() == a;
            assert second.range() == b;
            final Score score = new Score(first, second);
            assert score.range() == a + b - 1;
        }
    }

    @Test
    public void testToString() {
        final Score score = new Score("1d6 + 5");
        assert score.toString().equals("1d6+5") : score.toString();
        assert score.min() == 6 : score.min();
        assert score.max() == 11 : score.max();
        assert score.range() == 6 : score.range();
    }

    @Test
    public void equals() {
        assert new Score("2d12").equals(new Score("2d12"));
        assert new Score("2d12").equals(new Dice("2d12"));
        assert new Score("2d6").equals(new Score("1d11+1"));
        assert new Score("4d6").equals(new Score("1d21+3"));
    }

    @Test
    public void simplify() {
        assert this.effectivelyEqual(new Dice("2d12"), new Score("2d12"));
        assert this.simplify("2d12").equals("2d12") : this.simplify("2d12");
        assert this.effectivelyEqual(new Dice("2d6"), new Score("1d11+1"));
        assert this.simplify("1d11+1").equals("2d6") : this.simplify("1d11+1");
        assert this.effectivelyEqual(new Dice("2d3"), new Score("1d5+1"));
        assert this.simplify("1d5+1").equals("2d3") : this.simplify("1d5+1");
        assert this.effectivelyEqual(new Dice("4d6"), new Score("1d21+3"));
        assert this.simplify("1d21+3").equals("4d6") : this.simplify("1d21+3");
    }

    private String simplify(String counter) {
        return new Score(counter).simplify().toString();
    }

    private boolean effectivelyEqual(Rolled a, Rolled b) {
        assert a.max() == b.max();
        assert a.min() == b.min();
        assert a.range() == b.range();
        return true;
    }

}
