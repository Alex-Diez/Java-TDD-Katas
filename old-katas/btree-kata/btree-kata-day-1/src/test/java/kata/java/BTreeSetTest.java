package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    private BTreeSet set;

    @Before
    public void setUp() throws Exception {
        set = new BTreeSet();
    }

    @Test
    public void itShouldNotContain_whenKeyWasNotPutIn() throws Exception {
        assertThat(set.contains(1), is(false));
    }

    @Test
    public void itShouldContainValue_whenKeyWasPutIn() throws Exception {
        set.add(1);

        assertThat(set.contains(1), is(true));
    }

    @Test
    public void itShouldContainMultipleKeys() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
    }
}
