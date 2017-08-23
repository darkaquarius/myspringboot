package boot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by huishen on 17/7/28.
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsTextVo {

    private Integer userId;
    private List<Contact> text;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {
        private String name;
        private String tel;
    }

}
