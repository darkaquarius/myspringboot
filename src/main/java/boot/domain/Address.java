package boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huishen on 16/9/26.
 *
 */
@Entity
@Table(name = "address")
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
    private Date updateTime;
    private Date createTime;

    private User user;

    public Address(Integer id) {this.id = id;}
}
