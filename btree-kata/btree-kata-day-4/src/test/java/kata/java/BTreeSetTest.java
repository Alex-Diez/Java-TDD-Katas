package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    private static final int PAGE_LENGTH = 6;
    private BTreeSet set;

    @Before
    public void setUp() throws Exception {
        set = new BTreeSet(PAGE_LENGTH);
    }

    @Test
    public void itShouldNotContain_whenValueWasNotPutIn() throws Exception {
        assertThat(set.contains(1), is(false));
    }

    @Test
    public void itShouldContain_whenValueWasPutIn() throws Exception {
        set.add(1);

        assertThat(set.contains(1), is(true));
    }
    
    @Test
    public void itShouldContainMultipleKeys() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
    }
    
    @Test
    public void itShouldContainKeys_whenPageSplit() throws Exception {
        for (int i = 0; i < PAGE_LENGTH; i++) {
            set.add(i);
        }

        assertThat(set.contains(0), is(true));
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
        assertThat(set.contains(5), is(true));
    }

    @Test
    public void itShouldContainKeys_whenMidstPageIsSplit() throws Exception {
        for (int i = 0; i < 3; i++) {
            set.add(i);
        }
        for (int i = 7; i < 10; i++) {
            set.add(i);
        }
        for (int i = 3; i < 7; i++) {
            set.add(i);
        }

        assertThat(set.contains(0), is(true));
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
        assertThat(set.contains(5), is(true));
        assertThat(set.contains(6), is(true));
        assertThat(set.contains(7), is(true));
        assertThat(set.contains(8), is(true));
        assertThat(set.contains(9), is(true));
    }

    @Test
    public void itShouldContainKeys_whenRootPageIsSplitTwoTimes() throws Exception {
        for (int i = 0; i < PAGE_LENGTH * PAGE_LENGTH; i++) {
            set.add(i);
        }

        assertThat(set.contains(0), is(true));
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
        assertThat(set.contains(5), is(true));
        assertThat(set.contains(6), is(true));
        assertThat(set.contains(7), is(true));
        assertThat(set.contains(8), is(true));
        assertThat(set.contains(9), is(true));
        assertThat(set.contains(10), is(true));
        assertThat(set.contains(11), is(true));
        assertThat(set.contains(12), is(true));
        assertThat(set.contains(13), is(true));
        assertThat(set.contains(14), is(true));
        assertThat(set.contains(15), is(true));
        assertThat(set.contains(16), is(true));
        assertThat(set.contains(17), is(true));
        assertThat(set.contains(18), is(true));
        assertThat(set.contains(19), is(true));
        assertThat(set.contains(20), is(true));
        assertThat(set.contains(21), is(true));
        assertThat(set.contains(22), is(true));
        assertThat(set.contains(23), is(true));
        assertThat(set.contains(24), is(true));
        assertThat(set.contains(25), is(true));
        assertThat(set.contains(26), is(true));
        assertThat(set.contains(27), is(true));
        assertThat(set.contains(28), is(true));
        assertThat(set.contains(29), is(true));
        assertThat(set.contains(30), is(true));
        assertThat(set.contains(31), is(true));
        assertThat(set.contains(32), is(true));
        assertThat(set.contains(33), is(true));
        assertThat(set.contains(34), is(true));
        assertThat(set.contains(35), is(true));
    }
}
