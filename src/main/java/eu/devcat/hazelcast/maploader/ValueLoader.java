package eu.devcat.hazelcast.maploader;

import com.hazelcast.core.MapLoader;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ValueLoader implements MapLoader<Key, Value> {

    private final Random random = new Random();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Value load(Key key) {
        return new Value(key.getKey() + "#" + random.nextInt());
    }

    @Override
    public Map<Key, Value> loadAll(Collection<Key> keys) {
        System.out.format("Invocation %s, number of loading keys %s, Currently loading keys : %s \n",
                atomicInteger.incrementAndGet(),
                keys.size(),
                keys);
        return keys.stream()
                .collect(Collectors.toMap(x -> x, this::load));
    }

    @Override
    public Iterable<Key> loadAllKeys() {
        return IntStream.range(1, 10000)
                .mapToObj(Key::new)
                .collect(Collectors.toList());
    }
}
