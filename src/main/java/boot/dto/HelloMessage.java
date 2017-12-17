package boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by huishen on 17/12/16.
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage {

    private String name;

}
