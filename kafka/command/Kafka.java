package kafka.command;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author wulizi
 */
public class Kafka implements Closeable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Kafka kafka = new Kafka();
        kafka.describeTopicConfig("test");
    }

    private final AdminClient client;

    public Kafka() {
        Properties prop = new Properties();
        String brokerList = "localhost:9092";
        prop.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        prop.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        client = AdminClient.create(prop);
    }

    public void createTopic(String topic, int numPartition, short replicationFactor) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topic, numPartition, replicationFactor);
        CreateTopicsResult result = client.createTopics(Collections.singleton(newTopic));
        result.all().get();
    }

    public void describeTopicConfig(String topic) throws ExecutionException, InterruptedException {
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, topic);
        DescribeConfigsResult result = client.describeConfigs(Collections.singleton(resource));
        Config config = result.all().get().get(resource);
        System.out.println(config);
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
