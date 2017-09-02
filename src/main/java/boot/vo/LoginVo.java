package boot.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by huishen on 17/9/2.
 *
 */

@Data
@Builder
public class LoginVo {

    private Integer userId;
    private String name;
    private String token;

}
