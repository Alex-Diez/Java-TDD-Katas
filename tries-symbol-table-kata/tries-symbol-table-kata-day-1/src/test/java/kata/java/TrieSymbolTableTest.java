package kata.java;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.OptionalInt;

import static org.hamcrest.MatcherAssert.assertThat;

public class TrieSymbolTableTest {

    private TrieSymbolTable table;

    @Before
    public void setUp() throws Exception {
        table = new TrieSymbolTable();
    }

    @Test
    public void itShouldNotContainAnything_whenIsEmpty() throws Exception {
        assertThat(table.get("key"), OptionalIntMatchers.empty());
    }

    @Test
    public void itShouldContainPutValue() throws Exception {
        table.put("key", 1);

        assertThat(table.get("key"), OptionalIntMatchers.contains(1));
    }

    @Test
    public void itShouldEraseOldValueByNew() throws Exception {
        table.put("key", 1);
        table.put("key", 2);

        assertThat(table.get("key"), OptionalIntMatchers.contains(2));
    }

    @Test
    public void itShouldContainsMultipleDifferentValues() throws Exception {
        table.put("key-1", 1);
        table.put("key-2", 2);
        table.put("key-3", 3);

        assertThat(table.get("key-1"), OptionalIntMatchers.contains(1));
        assertThat(table.get("key-2"), OptionalIntMatchers.contains(2));
        assertThat(table.get("key-3"), OptionalIntMatchers.contains(3));
    }

    private static class OptionalIntMatchers {
        static Matcher<OptionalInt> empty() {
            return new TypeSafeMatcher<OptionalInt>() {
                @Override
                protected boolean matchesSafely(OptionalInt item) {
                    return !item.isPresent();
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("An empty Optional");
                }
            };
        }
        static Matcher<OptionalInt> contains(int content) {
            return new TypeSafeMatcher<OptionalInt>() {
                @Override
                protected boolean matchesSafely(OptionalInt item) {
                    return item.isPresent() && item.getAsInt() == content;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText(OptionalInt.of(content).toString());
                }
            };
        }
    }
}
