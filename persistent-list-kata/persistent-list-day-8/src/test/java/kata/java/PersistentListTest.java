package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersistentListTest {

    private PersistentList<Integer> empty;
    private PersistentList<Integer> nonempty;

    @Before
    public void setUp() throws Exception {
        empty = PersistentList.empty();
        nonempty = empty.prepend(10).prepend(20).prepend(30);
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
    public void headOfEmptyListIsEmptyOptional() throws Exception {
        assertThat(empty.head(), is(Optional.empty()));
    }
    
    @Test
    public void headOfNonemptyListIsTheFirstItem() throws Exception {
        assertThat(nonempty.head(), is(Optional.of(30)));
    }

    @Test
    public void tailOfEmptyListIsEmptyList() throws Exception {
        assertThat(empty.tail().toString(), is("[]"));
    }

    @Test
    public void tailOfNonemptyListIsAllItemsExceptTheFirst() throws Exception {
        assertThat(nonempty.tail().toString(), is("[20, 10]"));
    }
    
    @Test
    public void dropsNothingFromEmptyList() throws Exception {
        assertThat(empty.drop(10).toString(), is("[]"));
    }
    
    @Test
    public void dropsNothingFromNonemptyList() throws Exception {
        assertThat(nonempty.drop(0).toString(), is("[30, 20, 10]"));
        assertThat(nonempty.drop(-1).toString(), is("[30, 20, 10]"));
    }
    
    @Test
    public void dropsTwoItemsFromList() throws Exception {
        assertThat(nonempty.drop(2).toString(), is("[10]"));
    }

    @Test
    public void dropsNothingFromEmptyListByPredicate() throws Exception {
        assertThat(empty.dropWhile(i -> i > 10).toString(), is("[]"));
    }
    
    @Test
    public void dropsNothingFromNonemptyListByPredicate() throws Exception {
        assertThat(nonempty.dropWhile(i -> i > 50).toString(), is("[30, 20, 10]"));
    }

    @Test
    public void dropsTwoItemsFromNonemptyListByPredicate() throws Exception {
        assertThat(nonempty.dropWhile(i -> i > 10).toString(), is("[10]"));
    }

    @Test
    public void filtersOutNothingFromEmptyList() throws Exception {
        assertThat(empty.filter(i -> i == 10).toString(), is("[]"));
    }
    
    @Test
    public void filtersOutNothingFromNonemptyList() throws Exception {
        assertThat(nonempty.filter(i -> i > 0).toString(), is("[30, 20, 10]"));
    }

    @Test
    public void filtersOutTwoItemsFromList() throws Exception {
        assertThat(nonempty.filter(i -> i == 20).toString(), is("[20]"));
    }

    @Test
    public void emptyListReversesIntoItself() throws Exception {
        assertThat(empty.reverse().toString(), is("[]"));
    }

    @Test
    public void singleListReversesIntoItself() throws Exception {
        PersistentList single = PersistentList.empty().prepend(10);
        assertThat(single.reverse().toString(), is("[10]"));
    }

    @Test
    public void reversedListContainsItemsInReverseOrder() throws Exception {
        assertThat(nonempty.reverse().toString(), is("[10, 20, 30]"));
    }

    @Test
    public void mapContentToDouble() throws Exception {
        assertThat(nonempty.map(Integer::doubleValue).toString(), is("[30.0, 20.0, 10.0]"));
    }
}
