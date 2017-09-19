package boot.repository;

import boot.domain.LoanInfo;

/**
 * Created by huishen on 17/9/15.
 *
 */

public interface LoanInfoOperations {

    boolean updateFirst(LoanInfo loanInfo);

    boolean upset(LoanInfo loanInfo);

}
