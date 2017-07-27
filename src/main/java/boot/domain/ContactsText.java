package boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by huishen on 17/7/27.
 *
 */

@Entity
@Table(name = "contacts_text")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsText {
    private Integer id;
    private Integer userId;
    private String text;
    private Date updateTime;
    private Date createTime;

}
