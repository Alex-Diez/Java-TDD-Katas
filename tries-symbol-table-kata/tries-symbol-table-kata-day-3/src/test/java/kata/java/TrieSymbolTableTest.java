package kata.java;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import co.unruly.matchers.OptionalMatchers;

import java.util.OptionalInt;

import static org.hamcrest.MatcherAssert.assertThat;

public class TrieSymbolTableTest {

    private TrieSymbolTable<Integer> table;

    @Before
    public void setUp() throws Exception {
        table = new TrieSymbolTable<>();
    }

    @Test
    public void itShouldNotContainValue_whichNotPutIn() throws Exception {
        assertThat(table.get("key"), OptionalMatchers.empty());
    }

    @Test
    public void itShouldGetPutInValue() throws Exception {
        table.put("key", 1);

        assertThat(table.get("key"), OptionalMatchers.contains(1));
    }

    @Test
    public void itShouldEraseOldValueByNewOne() throws Exception {
        table.put("key", 1);
        table.put("key", 2);

        assertThat(table.get("key"), OptionalMatchers.contains(2));
    }

    @Test
    public void itShouldHavePossibilityToContainMultipleKeyValuePairs() throws Exception {
        table.put("key-1", 1);
        table.put("key-2", 2);
        table.put("key-3", 3);

        assertThat(table.get("key-1"), OptionalMatchers.contains(1));
        assertThat(table.get("key-2"), OptionalMatchers.contains(2));
        assertThat(table.get("key-3"), OptionalMatchers.contains(3));
    }
}
