package kata.java;

import org.junit.Ignore;
import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class ProbeMapTest {

    @Test
    public void createsMapWithDefaultCapacity() throws Exception {
        assertThat(new ProbeMap().capacity(), is(16));
    }

    @Test
    public void createsMapWithLesserCapacityThanDefault() throws Exception {
        assertThat(new ProbeMap(10).capacity(), is(16));
    }

    @Test
    public void createsMapWithCapacityGreaterThanDefault() throws Exception {
        assertThat(new ProbeMap(20).capacity(), is(32));
    }
    
    @Test
    public void createsMapWithDefaultMissedValue() throws Exception {
        assertThat(new ProbeMap().missedValue(), is(0));
    }
    
    @Test
    public void createsMapWitSpecifiedMissedValue() throws Exception {
        assertThat(new ProbeMap(16, Integer.MAX_VALUE).missedValue(), is(Integer.MAX_VALUE));
    }

    @Test
    public void putKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(1), is(OptionalInt.of(1)));
    }

    @Test
    public void getKeyThatDoesNotExistInMap() throws Exception {
        ProbeMap map = new ProbeMap();

        assertThat(map.get(8), is(OptionalInt.empty()));
    }

    @Test
    public void putManyKeys() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);

        assertThat(map.get(1), is(OptionalInt.of(2)));
        assertThat(map.get(2), is(OptionalInt.of(3)));
        assertThat(map.get(3), is(OptionalInt.of(4)));
    }

    @Test
    public void putBigKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1000, 1);

        assertThat(map.get(1000), is(OptionalInt.of(1)));
    }

    @Test
    public void putKeysWithCollision() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(17, 17);
        map.put(33, 33);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(17), is(OptionalInt.of(17)));
        assertThat(map.get(33), is(OptionalInt.of(33)));
    }

    @Test
    public void containsAllKeys() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);

        assertThat(map.keys(), containsInAnyOrder(IntStream.range(1, 4).boxed().toArray()));
    }

    @Test
    public void containsAllValues() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);

        assertThat(map.values(), containsInAnyOrder(IntStream.range(2, 5).boxed().toArray()));
    }
}
