package kata.java;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    private static final int PAGE_LENGTH = 6;
    private BTreeSet<Integer> set;

    @Before
    public void setUp() throws Exception {
        set = new BTreeSet<>(PAGE_LENGTH);
    }

    @Test
    public void doesNotContain_whenKeyWasNotPutIn() throws Exception {
        assertThat(set.contains(1), is(false));
    }

    @Test
    public void contain_whenKeyWasPutIn() throws Exception {
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
    
    @Test
    public void containKeys_whenRootPageIsSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH);
    }

    private void assertKeyRange(int from, int till) {
        for (int i = from; i < till; i++) {
            assertThat(set.contains(i), is(true));
        }
    }

    private void insertKeyRange(int from, int till) {
        for (int i = from; i < till; i++) {
            set.add(i);
        }
    }

    @Test
    public void containKeys_whenSubPageIsSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH / 2);
        assertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);
        assertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
    }

    @Test
    public void containKey_whenHighOrderRootPageSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);
    }
}
