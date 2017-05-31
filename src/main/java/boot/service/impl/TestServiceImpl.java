package boot.service.impl;

import boot.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/5/9.
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void test1() {
        System.out.println("he");
    }

}
