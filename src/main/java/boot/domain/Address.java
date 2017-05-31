package boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huishen on 16/9/26.
 */
@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String country;
    private String province;
    private String city;
    private User user;
    private Date updateTime;
    private Date createTime;

    public Address(Integer id) {this.id = id;}
}
