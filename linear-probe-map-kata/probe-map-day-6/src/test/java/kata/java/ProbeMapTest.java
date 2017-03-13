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
        assertThat(new ProbeMap(16, 0).missedValue(), is(0));
    }

    @Test
    public void putOneKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);

        assertThat(map.get(1), is(OptionalInt.of(1)));
        assertThat(map.get(1), is(OptionalInt.of(1)));
    }

    @Test
    public void putTheSameKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(1, 2);

        assertThat(map.get(1), is(OptionalInt.of(2)));
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
    public void getKeyThatDoesNotExist() throws Exception {
        ProbeMap map = new ProbeMap();

        assertThat(map.get(1), is(OptionalInt.empty()));
    }

    @Test
    public void putBigKey() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1000, 1000);

        assertThat(map.get(1000), is(OptionalInt.of(1000)));
    }

    @Test
    public void putKeysWithCollision() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(0, 0);
        map.put(16, 16);
        map.put(32, 32);

        assertThat(map.get(0), is(OptionalInt.of(0)));
        assertThat(map.get(16), is(OptionalInt.of(16)));
        assertThat(map.get(32), is(OptionalInt.of(32)));
    }

    @Test
    public void mapKeys() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);

        assertThat(map.keys(), containsInAnyOrder(IntStream.range(1, 4).boxed().toArray()));
    }

    @Test
    public void mapValues() throws Exception {
        ProbeMap map = new ProbeMap();

        map.put(1, 11);
        map.put(2, 12);
        map.put(3, 13);

        assertThat(map.values(), containsInAnyOrder(IntStream.range(11, 14).boxed().toArray()));
    }

    @Test
    public void putKeysMoreThanCapacity() throws Exception {
        ProbeMap map = new ProbeMap();

        IntStream.range(0, 20).forEach(key -> map.put(key, key + 20));

        assertThat(map.capacity(), is(32));
        assertThat(map.keys(), containsInAnyOrder(IntStream.range(0, 20).boxed().toArray()));
        assertThat(map.values(), containsInAnyOrder(IntStream.range(20, 40).boxed().toArray()));
    }

    @Test
    public void putKeyWithCollisionMoreThanCapacity() throws Exception {
        ProbeMap map = new ProbeMap();

        IntStream.iterate(0, item -> item + 16).limit(20).forEach(key -> map.put(key, key + 20));

        assertThat(map.capacity(), is(32));
        assertThat(map.keys(), containsInAnyOrder(IntStream.iterate(0, item -> item + 16).limit(20).boxed().toArray()));
        assertThat(map.values(), containsInAnyOrder(IntStream.iterate(20, item -> item + 16).limit(20).boxed().toArray()));
    }
}
