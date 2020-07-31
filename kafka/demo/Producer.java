package kafka.demo;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * kafka生产者
 *
 * @author wulizi
 */
public class Producer {
    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "test";
    private static final long EXPIRE_INTERVAL = 10*1000;

    private static Properties init() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                CompanySerializer.class.getName());
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,MyInterceptor.class.getName());
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,MyPartition.class.getName());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);

        return properties;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = init();
        Company company = Company.builder().name("伍立子").address("1998").build();
        Company company1 = Company.builder().name("伍立子1").address("1998").build();
        Company company2 = Company.builder().name("伍立子2").address("1998").build();
        KafkaProducer<String, Company> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, Company> record = new ProducerRecord<>(TOPIC, 0,
                System.currentTimeMillis()-EXPIRE_INTERVAL,null,company);
        producer.send(record).get();
        ProducerRecord<String, Company> record1 = new ProducerRecord<>(TOPIC, 0,
                System.currentTimeMillis(),null,company1);
        producer.send(record1).get();
        ProducerRecord<String, Company> record2 = new ProducerRecord<>(TOPIC, 0,
                System.currentTimeMillis()-EXPIRE_INTERVAL,null,company2);
        producer.send(record2).get();
        producer.close();

    }
}
