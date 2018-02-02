package boot.repository;

import boot.domain.LoanInfo;
import com.mongodb.BulkWriteResult;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.BulkOperations.BulkMode.ORDERED;

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

    @Override
    public List<LoanInfo> find(Integer age) {
        Criteria where = Criteria.where("age").is(30);
        // todo byExample
        Query query = new Query(where);
        query.limit(3);
        query.with(new Sort(Sort.Direction.DESC, "name"));
        query.fields().include("_id");
        query.fields().include("age");
        query.fields().include("name");
        query.fields().include("contact");

        return mongoTemplate.find(query, LoanInfo.class);
    }

    @Override
    public void saveByMongoTemplate() {
        LoanInfo loanInfo = LoanInfo.builder()
            .age(1000)
            .build();

        // 方法1: save()
        mongoTemplate.save(loanInfo, "loanInfo");
        System.out.println(loanInfo);
    }

    @Override
    public void bulkOps() {
        LoanInfo loanInfo = LoanInfo.builder()
            .age(1000)
            .build();
        BulkOperations bulkOps = mongoTemplate.bulkOps(ORDERED, LoanInfo.class);
        // 1. 更新
        // 根据'_id'来查询
        Query query = Query.query(Criteria.where("_id").is(new ObjectId("59f9bdab77c89d573def496f")));
        Update update = new Update();
        // update.inc("age", 100);
        update.set("isRich",false);
        BulkOperations bulkOperations = bulkOps.updateOne(query, update);
        // 2. 新建
        BulkOperations insert = bulkOps.insert(loanInfo);
        BulkWriteResult execute = bulkOps.execute();
        System.out.println(execute);
    }

    @Override
    public void updateByMongoTemplate() {
        Query query = Query.query(Criteria.where("_id").is(new ObjectId("59f9bdab77c89d573def496f")));
        Update update = new Update();
        update.set("isRich",true);
        WriteResult writeResult = mongoTemplate.updateFirst(query, update, LoanInfo.class);
        System.out.println(writeResult);
    }

}
