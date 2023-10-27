package besu.feeders;

import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlockRangeFeeder {

    public Iterator<Map<String, Object>> getBlockRange() {
        return Stream.generate(
                        (Supplier<Map<String, Object>>)
                                () -> {
                                    Long startBlock = RandomUtils.nextLong(0, 723_810);
                                    Long valueFromStartBlock = startBlock + 20;
                                    return Map.of(
                                            "id", RandomUtils.nextLong(),
                                            "startBlock", startBlock,
                                            "endBlock", valueFromStartBlock);
                                })
                .iterator();
    }
}