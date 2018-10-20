package eu.devcat.hazelcast.maploader;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HazelcastMapLoaderMain {

    private static final String CACHE_NAME = "valueCache";

    public static void main(String... args) {
        //configuration for cache
        Config cfg = new Config();
        final MapConfig mapConfig = createMapConfig();
        cfg.addMapConfig(mapConfig);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);

        IMap<Key, Value> mapCustomers = instance.getMap(CACHE_NAME);

        //init cache
        mapCustomers.loadAll(true);

        //closing hazelcast
        instance.shutdown();
    }

    private static MapConfig createMapConfig() {
        final MapConfig mapConfig = new MapConfig(CACHE_NAME);
        final MapStoreConfig mapStoreConfig = createMapStoreConfig();
        mapConfig.setMapStoreConfig(mapStoreConfig);
        return mapConfig;
    }

    private static MapStoreConfig createMapStoreConfig() {
        final MapStoreConfig mapStoreConfig = new MapStoreConfig();
        mapStoreConfig.setImplementation(new ValueLoader());
        return mapStoreConfig;
    }

}
