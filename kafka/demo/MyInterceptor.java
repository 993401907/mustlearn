package kafka.demo;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wulizi
 * 生产者拦截器
 */
public class MyInterceptor implements ProducerInterceptor<String, String> {
    private final AtomicInteger successCount = new AtomicInteger();
    private final AtomicInteger failureCount = new AtomicInteger();

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception exception) {
        if (exception == null) {
            successCount.getAndIncrement();
        } else {
            failureCount.getAndIncrement();
        }
    }

    @Override
    public void close() {
        double successRatio = (double) successCount.get() / (successCount.get() + failureCount.get());
        System.out.println("发送成功率是: " + String.format("%f", successRatio * 100) + "%");
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
