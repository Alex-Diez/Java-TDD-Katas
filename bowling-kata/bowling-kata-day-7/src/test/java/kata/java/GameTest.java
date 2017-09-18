package kata.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GameTest {

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

    @BeforeEach
    void setUp() throws Exception {
        game = new Game();
    }

    @Test
    void gutterGame() throws Exception {
        rollMany(20, 0);

        assertThat(game.score(), is(0));
    }

    @Test
    void allOnes() throws Exception {
        rollMany(20, 1);

        assertThat(game.score(), is(20));
    }

    @Test
    void oneSpare() throws Exception {
        rollSpare();
        game.roll(3);
        rollMany(17, 0);

        assertThat(game.score(), is(16));
    }

    @Test
    void oneStrike() throws Exception {
        game.roll(10);
        game.roll(4);
        game.roll(3);
        rollMany(16, 0);

        assertThat(game.score(), is(24));
    }

    @Test
    void perfectGame() throws Exception {
        rollMany(12, 10);

        assertThat(game.score(), is(300));
    }
}
