package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    public static final int PAGE_LENGTH = 6;
    private BTreeSet set;

    @Before
    public void setUp() throws Exception {
        set = new BTreeSet(PAGE_LENGTH);
    }

    @Test@Ignore
    public void doesNotContain_whenValueWasNotPutIn() throws Exception {
        assertThat(set.contains(1), is(false));
    }

    @Test
    public void containValueThatWasPutIn() throws Exception {
        set.add(1);

        assertThat(set.contains(1), is(true));
    }

    @Test
    public void containMultipleKeys() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.contains(4), is(true));
    }

    @Test@Ignore
    public void containKeysAfterPageSplitting() throws Exception {
        insertKeyRange(0, PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH);
    }

    private void assertKeyRange(int from, int to) {
        for (int i = from; i < to; i++) {
            assertThat(set.contains(i), is(true));
        }
    }

    private void insertKeyRange(int from, int to) {
        for (int i = from; i < to; i++) {
            set.add(i);
        }
    }

    @Test@Ignore
    public void containKeysAfterSubPageSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH / 2);
        assertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);
        assertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
    }

    @Test@Ignore
    public void moreThenTwoLevelGrow() throws Exception {
        insertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);
    }
}
