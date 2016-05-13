package kata.java;

import co.unruly.matchers.OptionalMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class TrieSymbolTableTest {

    private TrieSymbolTable<Integer> table;

    @Before
    public void setUp() throws Exception {
        table = new TrieSymbolTable<>();
    }

    @Test
    public void itShouldNotContain_whenValueWasNotPutIn() throws Exception {
        assertThat(table.get("key"), OptionalMatchers.empty());
    }

    @Test
    public void itShouldContain_whenValueWasPutIn() throws Exception {
        table.put("key", 1);

        assertThat(table.get("key"), OptionalMatchers.contains(1));
    }

    @Test
    public void itShouldOverrideOldValueByNew() throws Exception {
        table.put("key", 1);
        table.put("key", 2);

        assertThat(table.get("key"), OptionalMatchers.contains(2));
    }

    @Test
    public void itShouldContainMultipleKeyValuePairs() throws Exception {
        table.put("key-1", 1);
        table.put("key-2", 2);
        table.put("key-3", 3);

        assertThat(table.get("key-1"), OptionalMatchers.contains(1));
        assertThat(table.get("key-2"), OptionalMatchers.contains(2));
        assertThat(table.get("key-3"), OptionalMatchers.contains(3));
    }
}
