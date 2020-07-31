package kafka.demo;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;
import java.util.Optional;

/**
 * @author wulizi
 */
public class CompanyDeserializer implements Deserializer<String > {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public String  deserialize(String topic, byte[] data) {
        return Optional.ofNullable(data).map(d -> {
            Schema<Company> schema = RuntimeSchema.getSchema(Company.class);
            Company res = new Company();
            ProtostuffIOUtil.mergeFrom(d, res, schema);
            return res.toString();
        }).orElse(null);
    }
}
