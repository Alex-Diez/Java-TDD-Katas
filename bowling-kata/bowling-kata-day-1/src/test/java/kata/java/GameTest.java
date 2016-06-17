package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameTest {

    private Game game;

    private void rollMany(int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    private void rollSpare() {
        game.roll(5);
        game.roll(5);
    }

    private void rollStrike() {
        game.roll(10);
    }

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void gutterGame() throws Exception {
        rollMany(20, 0);

        assertThat(new GameScoreCalc().score(game), is(0));
    }

    @Test
    public void allOnes() throws Exception {
        rollMany(20, 1);

        assertThat(new GameScoreCalc().score(game), is(20));
    }

    @Test
    public void oneSpare() throws Exception {
        rollSpare();
        game.roll(3);
        rollMany(17, 0);

        assertThat(new GameScoreCalc().score(game), is(16));
    }

    @Test
    public void oneStrike() throws Exception {
        rollStrike();
        game.roll(4);
        game.roll(3);
        rollMany(16, 0);

        assertThat(new GameScoreCalc().score(game), is(24));
    }

    @Test
    public void perfectGame() throws Exception {
        rollMany(12, 10);

        assertThat(new GameScoreCalc().score(game), is(300));
    }
}
