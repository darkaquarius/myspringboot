package boot.repository;

import boot.domain.LoanInfo;

import java.util.List;

/**
 * Created by huishen on 17/9/15.
 *
 *
 * mongoTemplate 接口
 *
 */

public interface LoanInfoOperations {

    boolean updateFirst(LoanInfo loanInfo);

    boolean upset(LoanInfo loanInfo);

    List<LoanInfo> find(Integer age);

    void saveByMongoTemplate();

    // void bulkOps();

    void updateByMongoTemplate();

}
