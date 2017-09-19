package boot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by huishen on 17/9/15.
 *
 */

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdfaStatistics {

    @Id
    private String id;

    private String sourceApp;
    private Long amount;
    private Set<String> idfas;
    private LocalDate createDate;

}
