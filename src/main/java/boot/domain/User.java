package boot.domain;

import boot.consts.Jobs;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huishen on 16/9/24.
 *
 */
@Component
//@ConfigurationProperties(prefix = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private Boolean sex;
    private Jobs job;
    private String remark;
    private Date updateTime;
    private Date createTime;

    //去掉@Value
    //使用@ConfigurationProperties需要指定prefix,同时bean中的属性和配置参数名保持一致
//    private String address;

    //一端控制多端
//    private List<Address> allDetailAddress = new ArrayList<Address>();

    public User(Integer id){
        this.id = id;
    }

}
