package kafka.demo;


import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.Optional;

/**
 * 公司的序列化
 *
 * @author wulizi
 */
public class CompanySerializer implements Serializer<Company> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Company company) {
        return Optional.ofNullable(company).map(data -> {
            Schema<Company> schema = RuntimeSchema.getSchema(Company.class);
            LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
            return ProtostuffIOUtil.toByteArray(data, schema, buffer);
        }).orElse(null);
    }

    @Override
    public void close() {
    }
}
