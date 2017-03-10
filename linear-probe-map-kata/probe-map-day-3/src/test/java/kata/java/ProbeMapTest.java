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
    public void createsMapWithCapacityLesserThanDefault() throws Exception {
        assertThat(new ProbeMap(10).capacity(), is(16));
    }

    @Test
    public void createsMapWithCapacityGreaterThanDefault() throws Exception {
        assertThat(new ProbeMap(20).capacity(), is(32));
    }

    @Test
    public void createsMapWithDefaultMissedValue() throws Exception {
        assertThat(new ProbeMap().missedValue(), is(Integer.MIN_VALUE));
    }
    
    @Test
    public void createsMapWithSpecifiedMissedValue() throws Exception {
        assertThat(new ProbeMap(16, Integer.MAX_VALUE).missedValue(), is(Integer.MAX_VALUE));
    }

    @Test
    public void putOneLey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(1), is(OptionalInt.of(1)));
    }
    
    @Test
    public void putTheSameKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 2);
        map.put(1, 3);

        assertThat(map.get(1), is(OptionalInt.of(3)));
    }
    
    @Test
    public void putManyKeys() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(2), is(OptionalInt.of(2)));
        assertThat(map.get(3), is(OptionalInt.of(3)));
    }

    @Test
    public void getKeyThatDoesNotExistInMap() throws Exception {
        ProbeMap map = new ProbeMap();

        assertThat(map.get(1), is(OptionalInt.empty()));
    }

    @Test
    public void putBigKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1000, 1);

        assertThat(map.get(1000), is(OptionalInt.of(1)));
    }

    @Test
    public void putWithKeyCollision() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(17, 17);
        map.put(33, 33);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(17), is(OptionalInt.of(17)));
        assertThat(map.get(33), is(OptionalInt.of(33)));
    }

    @Test
    public void mapKeys() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);

        assertThat(map.keys(), containsInAnyOrder(IntStream.range(1, 5).boxed().toArray()));
    }

    @Test
    public void mapValues() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 11);
        map.put(2, 12);
        map.put(3, 13);
        map.put(4, 14);

        assertThat(map.values(), containsInAnyOrder(IntStream.range(11, 15).boxed().toArray()));
    }
}
