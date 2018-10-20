package eu.devcat.hazelcast.maploader;

import com.hazelcast.core.MapLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ValueLoader implements MapLoader<Key, Value> {

    private static final Logger logger = LogManager.getLogger(ValueLoader.class);
    private final Random random = new Random();
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Value load(Key key) {
        return new Value(key.getKey() + "#" + random.nextInt());
    }

    @Override
    public Map<Key, Value> loadAll(Collection<Key> keys) {
        logger.info("Invocation {}, number of loading keys {}, Currently loading keys : {}",
                atomicInteger.incrementAndGet(),
                keys.size(),
                keys);
        return keys.stream()
                .collect(Collectors.toMap(x -> x, this::load));
    }

    @Override
    public Iterable<Key> loadAllKeys() {
        return IntStream.range(1, 1_000_000)
                .mapToObj(Key::new)
                .collect(Collectors.toList());
    }
}
