package boot.domain;

import boot.consts.Jobs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by huishen on 16/9/24.
 *
 */
//@ConfigurationProperties(prefix = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

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

    //一端控制多端
    private List<Address> addresses;

    //去掉@Value
    //使用@ConfigurationProperties需要指定prefix,同时bean中的属性和配置参数名保持一致
//    private String address;

    public User(Integer id){
        this.id = id;
    }

}
