package kafka.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司实体类
 *
 * @author wulizi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Company {
    private String name;
    private String address;
}
