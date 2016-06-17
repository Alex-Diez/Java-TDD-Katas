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
    public void setDoesNotContainKey_whenKeyWasNotPutIn() throws Exception {
        assertThat(set.contains(1), is(false));
    }

    @Test
    public void setContainsKey_whenKeyWasPutIn() throws Exception {
        set.add(1);

        assertThat(set.contains(1), is(true));
    }

    @Test
    public void setIsAbleToContainMultipleKeys() throws Exception {
        insertKeyRange(0, PAGE_LENGTH - 1);

        assertKeyRange(0, PAGE_LENGTH - 1);
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
    public void setShouldContainAllKeys_whenRootPageIsSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH + 1);

        assertKeyRange(0, PAGE_LENGTH + 1);
        assertThat(set.height(), is(1));
    }

    @Test
    public void setShouldContainAllKeys_whenSubPageIsSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
        insertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH / 2);
        assertKeyRange(PAGE_LENGTH / 2, PAGE_LENGTH);
        assertKeyRange(PAGE_LENGTH, PAGE_LENGTH + PAGE_LENGTH / 2);
        assertThat(set.height(), is(1));
    }

    @Test
    public void setShouldContainAllKeys_whenHighOrderRootPageIsSplit() throws Exception {
        insertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);

        assertKeyRange(0, PAGE_LENGTH * PAGE_LENGTH);
        assertThat(set.height(), is(2));
    }
}
