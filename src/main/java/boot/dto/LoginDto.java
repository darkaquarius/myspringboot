package boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by huishen on 17/9/2.
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String name;
    private String password;

}
