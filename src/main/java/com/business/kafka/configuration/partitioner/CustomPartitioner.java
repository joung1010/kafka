package com.business.kafka.configuration.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes,
                         Object value, byte[] valueBytes, Cluster cluster) {

        if (keyBytes == null) {
            throw new InvalidRecordException("Message key is required");
        }

        // "Pangyo" 키는 항상 파티션 0으로
        if ("Pangyo".equals(key)) {
            return 0;
        }

        // 그 외 키는 기본 해시 파티셔닝
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }

    @Override
    public void close() {
        // 리소스 정리 로직 (필요시)
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // 초기화 로직 (필요시)
    }
}