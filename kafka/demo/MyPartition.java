package kafka.demo;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wulizi
 * 自定义分区
 */
public class MyPartition implements Partitioner {
    private final AtomicInteger counter = new AtomicInteger();
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int partitionNum = partitionInfos.size();
        return Optional.ofNullable(keyBytes)
                .map(kb -> Utils.toPositive(Utils.murmur2(kb)) % partitionNum)
                .orElse(counter.getAndIncrement() % partitionNum);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
