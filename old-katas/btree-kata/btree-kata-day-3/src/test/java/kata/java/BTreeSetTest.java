package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    private static final int PAGE_LENGTH = 5;

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
    public void itShouldHaveOnePageHeight_whenSetIsEmpty() throws Exception {
        assertThat(set.height(), is(1));
    }

    @Test
    public void itShouldIncreaseHeight_whenRootPageIsCompletelyFilled() throws Exception {
        for (int i = 0; i < PAGE_LENGTH + 1; i++) {
            set.add(i);
        }

        assertThat(set.height(), is(2));
    }

    @Test
    public void itShouldStillContainKeys_whenRootPageIsCompletelyFilled() throws Exception {
        for (int i = 0; i < PAGE_LENGTH + 1; i++) {
            set.add(i);
        }

        assertThat(set.contains(0), is(true));
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
        assertThat(set.contains(5), is(true));
    }
    
    @Test@Ignore
    public void itShouldNotIncreaseHeight_whenChildPageSplits() throws Exception {
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
}
