package boot.repository;

import boot.domain.LoanInfo;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;

/**
 * Created by huishen on 17/9/15.
 *
 */

public class LoanInfoRepositoryImpl implements LoanInfoOperations {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean updateFirst(LoanInfo loanInfo) {
        Criteria where = Criteria.where("name").is(loanInfo.getName());
        Query query = Query.query(where);
        // query.addCriteria(criteria);

        Update update = new Update();
        update.set("age", loanInfo.getAge());

        WriteResult result = mongoTemplate.updateFirst(query, update, LoanInfo.class);
        return true;
    }

    @Override
    public boolean upset(LoanInfo loanInfo) {
        Criteria where = Criteria.where("name").is(loanInfo.getName()).and("age").is(loanInfo.getAge());
        Query query = Query.query(where);

        Update update = new Update();
        update.set("idfas", Arrays.asList("659C9115-15B7-4537-91EC-6621D5AC8C56","EA0A9C93-6ED6-45E8-8F01-E68CD964DCE0"));

        WriteResult upsert = mongoTemplate.upsert(query, update, LoanInfo.class);
        return true;
    }

}
