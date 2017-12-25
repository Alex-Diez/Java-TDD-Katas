package kata.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
    public void prependManyItemsToList() throws Exception {
        assertThat(empty.prepend(10).prepend(20).prepend(30).toString(), is("[30, 20, 10]"));
    }

    @Test
    public void appendItemsToList() throws Exception {
        assertThat(empty.append(10).append(20).append(30).toString(), is("[10, 20, 30]"));
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
    public void forAllIsAlwaysTrueForEmptyList() throws Exception {
        assertThat(empty.forAll(i -> i > 10), is(true));
    }

    @Test
    public void forAllForNonemptyListIsTrueIfAllItemsMatchAPredicate() throws Exception {
        assertThat(nonempty.forAll(i -> i > 0), is(true));
        assertThat(nonempty.forAll(i -> i > 10), is(false));
        assertThat(nonempty.forAll(i -> i > 20), is(false));
        assertThat(nonempty.forAll(i -> i > 30), is(false));
    }

    @Test
    public void concatenationOfTwoEmptyListIsAnEmptyList() throws Exception {
        assertThat(empty.concat(empty).toString(), is("[]"));
    }

    @Test
    public void concatenationOfNonemptyAndEmptyListIsTheNonemptyList() throws Exception {
        assertThat(nonempty.concat(empty).toString(), is("[30, 20, 10]"));
        assertThat(empty.concat(nonempty).toString(), is("[30, 20, 10]"));
    }

    @Test
    public void concatenationOfTwoNonemptyListIsTheListContainsItemsFromBothLists() throws Exception {
        PersistentList<Integer> listOne = nonempty;
        PersistentList<Integer> listTwo = empty.prepend(40).prepend(50).prepend(60);

        assertThat(listOne.concat(listTwo).toString(), is("[30, 20, 10, 60, 50, 40]"));
        assertThat(listTwo.concat(listOne).toString(), is("[60, 50, 40, 30, 20, 10]"));
    }

    @Test
    public void dropsNothingFromEmptyList() throws Exception {
        assertThat(empty.drop(10).toString(), is("[]"));
    }

    @Test
    public void dropsNothingFromNonemptyList() throws Exception {
        assertThat(nonempty.drop(0).toString(), is("[30, 20, 10]"));
    }

    @Test
    public void dropsTwoItemsFromNonemptyList() throws Exception {
        assertThat(nonempty.drop(2).toString(), is("[10]"));
    }

    @Test
    public void dropsNoneItemFromEmptyListByPredicate() throws Exception {
        assertThat(empty.dropWhile(i -> i < 10).toString(), is("[]"));
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
    public void filtersOutNothingFromAnEmptyList() throws Exception {
        assertThat(empty.filter(i -> i < 10).toString(), is("[]"));
    }
    
    @Test
    public void filtersOutNothingFromANonemptyList() throws Exception {
        assertThat(nonempty.filter(i -> i > 0).toString(), is("[30, 20, 10]"));
    }
    
    @Test
    public void filtersOutTwoItemsFromNonemptyList() throws Exception {
        assertThat(nonempty.filter(i -> i > 20).toString(), is("[30]"));
    }

    @Test
    public void emptyListReversedIntoEmptyList() throws Exception {
        assertThat(empty.reverse().toString(), is("[]"));
    }

    @Test
    public void singleElementListReverseInItself() throws Exception {
        PersistentList singleton = empty.prepend(10);
        assertThat(singleton.reverse().toString(), is("[10]"));
    }

    @Test
    public void reversedListContainsTheSameItemsInReverseOrder() throws Exception {
        assertThat(nonempty.reverse().toString(), is("[10, 20, 30]"));
    }

    @Test
    public void mapsContentToDoubles() throws Exception {
        assertThat(nonempty.map(Integer::doubleValue).toString(), is("[30.0, 20.0, 10.0]"));
    }
}
