package boot.demo;

import boot.BaseTest;
import lombok.Builder;
import org.bson.types.Decimal128;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.math.BigDecimal;

/**
 * @author huishen
 * @date 18/4/25 下午4:11
 */
public class MongoDemo extends BaseTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test1() {
        Test1 test1 = Test1.builder().mount(new Decimal128(10)).build();
        mongoTemplate.save(test1, "test1");
    }

    @Test
    public void test2() {
        Query query = Query.query(Criteria.where("_id").is("5ae0399977c889ee1c5dfdce"));
        Update update = new Update();
        update.inc("mount", BigDecimal.valueOf(0.5));
        mongoTemplate.updateFirst(query, update, Test1.class);
    }

    @Document
    @Builder
    public static class Test1 {
        @Id
        private String id;
        private Decimal128 mount;

    }

}
