package boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by huishen on 17/9/15.
 *
 */

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanInfo {

    @Id
    private String id;

    private String name;
    private Sex sex;
    private Integer age;
    private String tel;
    private Boolean isRich;
    private Double weight;
    private BigDecimal deposit;    // 存款
    private String description;
    private Contact contact;
    private List<Loan> loans;
    private List<String> idfas;
    private LocalDateTime createDate;


    public enum Sex {
        MALE,
        FEMALE,
        ;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {
        private String name;
        private Integer age;
        private String tel;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Loan {
        private BigDecimal amount;
        private Double rate;
        private Integer period;
        private LocalDate startDate;
    }

}
