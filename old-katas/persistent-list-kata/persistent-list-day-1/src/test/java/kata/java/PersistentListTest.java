package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersistentListTest {

    private PersistentList empty;

    @Before
    public void setUp() throws Exception {
        empty = PersistentList.empty();
    }

    @Test
    public void createEmptyList() throws Exception {
        assertThat(empty.toString(), is("[]"));
    }

    @Test
    public void prependItemToList() throws Exception {
        assertThat(empty.prepend(10).toString(), is("[10]"));
    }

    @Test
    public void prependManyItems() throws Exception {
        assertThat(empty.prepend(10).prepend(20).prepend(30).toString(), is("[30, 20, 10]"));
    }
    
    @Test
    public void appendManyItems() throws Exception {
        assertThat(empty.append(10).append(20).toString(), is("[10, 20]"));
    }

    @Test
    public void headOfEmptyListIsNone() throws Exception {
        assertThat(empty.head(), is(Optional.empty()));
    }
    
    @Test
    public void headOfNonemptyListIsTheFirstItem() throws Exception {
        assertThat(empty.prepend(10).prepend(20).head(), is(Optional.of(20)));
    }
    
    @Test
    public void tailOfEmptyListIsEmptyList() throws Exception {
        assertThat(empty.tail().toString(), is("[]"));
    }
    
    @Test
    public void tailOfNonemptyListIsAllItemsExceptTheFirst() throws Exception {
        PersistentList nonempty = empty.prepend(10).prepend(20).prepend(30);
        assertThat(nonempty.tail().toString(), is("[20, 10]"));
    }
    
    @Test
    public void forAllAlwaysTrueForEmptyList() throws Exception {
        assertThat(empty.forAll(i -> i == 10), is(true));
    }
    
    @Test
    public void forAllForNonemptyListTrueIfAllItemsMatchPredicate() throws Exception {
        PersistentList nonempty = empty.prepend(10).prepend(20).prepend(30);
        assertThat(nonempty.forAll(i -> i > 0), is(true));
        assertThat(nonempty.forAll(i -> i > 10), is(false));
        assertThat(nonempty.forAll(i -> i > 20), is(false));
        assertThat(nonempty.forAll(i -> i > 30), is(false));
    }
}
