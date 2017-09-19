package boot.service.impl;

import boot.domain.LoanInfo;
import boot.repository.LoanInfoRepository;
import boot.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/9/15.
 *
 */

@Service
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoRepository loanInfoRepository;

    @Override
    public void save(LoanInfo loanInfo) {
        LoanInfo save = loanInfoRepository.save(loanInfo);
    }



}
