package kata.java;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BTreeSetTest {

    private BTreeSet set;

    @Before
    public void setUp() throws Exception {
        set = new BTreeSet(5);
    }

    @Test
    public void itShouldNotContain_whenValueWasNotPutIn() throws Exception {
        assertThat(set.contain(1), is(false));
    }

    @Test
    public void itShouldContain_whenValueWasPutIn() throws Exception {
        set.add(1);

        assertThat(set.contain(1), is(true));
    }
    
    @Test
    public void itShouldNotIncreaseHeight() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        assertThat(set.height(), is(1));
    }

    @Test
    public void itShouldContainMultipleKeys() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        assertThat(set.contain(1), is(true));
        assertThat(set.contain(2), is(true));
        assertThat(set.contain(3), is(true));
        assertThat(set.contain(4), is(true));
    }

    @Test
    public void itShouldIncreaseHeight_whenWasInsertedMoreKeysThatRootPageSize() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);

        assertThat(set.height(), is(2));
    }

    @Test
    public void itShouldContainKeys_whenHeightWasIncreased() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);

        assertThat(set.contain(1), is(true));
        assertThat(set.contain(2), is(true));
        assertThat(set.contain(3), is(true));
        assertThat(set.contain(4), is(true));
        assertThat(set.contain(5), is(true));
        assertThat(set.contain(6), is(true));
    }

    @Test@Ignore
    public void itShouldContainKeys_whenPageSplitInBetween() throws Exception {
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(7);
        set.add(8);
        set.add(9);
        set.add(4);
        set.add(5);
        set.add(6);

        assertThat(set.contain(1), is(true));
        assertThat(set.contain(2), is(true));
        assertThat(set.contain(3), is(true));
        assertThat(set.contain(4), is(true));
        assertThat(set.contain(5), is(true));
        assertThat(set.contain(6), is(true));
        assertThat(set.contain(7), is(true));
        assertThat(set.contain(8), is(true));
        assertThat(set.contain(9), is(true));
    }
}
