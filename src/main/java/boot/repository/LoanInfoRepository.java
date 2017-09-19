package boot.repository;

import boot.domain.LoanInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by huishen on 17/9/15.
 *
 */

public interface LoanInfoRepository extends MongoRepository<LoanInfo, String>, LoanInfoOperations {

    LoanInfo findByName(String name);

    // 根据第一个参数查询
    @Query(value = "{'name':?0}")
    LoanInfo findByCustom01(String name);

    // 同时满足2个条件 $and
    @Query(value = "{$and:[{'name':?0},{'age':?1}]}")
    LoanInfo findByCustom02(String name, Integer age);

    // 满足第一个或者第二个条件 $or
    @Query(value = "{$or:[{'name':?0},{'age':?1}]}")
    List<LoanInfo> findByCustom03(String name, Integer age);

    // 对查询所返回的字段进行筛选
    @Query(value = "{$or:[{'name':?0},{'age':?1}]}", fields = "{'name':1, 'age':1, '_id':0}")
    List<LoanInfo> findByCustom04(String name, Integer age);

    // 返回满足条件的文档数
    @Query(value = "{$or:[{'name':?0},{'age':?1}]}", count = true)
    long findByCustom05(String name, Integer age);

    // 返回是否存在满足条件的文档
    @Query(value = "{$or:[{'name':?0},{'age':?1}]}", exists = true)
    boolean findByCustom06(String name, Integer age);

    // 比较操作符, $lt, $lte, $gt, $gte, $ne
    @Query(value = "{'age':{$gte:50}}")
    List<LoanInfo> findByCustom07(Integer age);

    // 比较日期时间
    @Query(value = "{'createDate':{$gte:?0}}")
    List<LoanInfo> findByCustom08(LocalDate localDate);

    // @Query(value = "{'name':{$in:['张三'，'李四']}}")
    // List<LoanInfo> findByCustom09();

    // $not:{}
    @Query(value = "{'age':{$not:{$gt:?0}}}")
    List<LoanInfo> findByCustom10(Integer age);

    // 年龄大于50，小于60
    @Query("{'age':{$gt:?0,$lt:?1}}")
    List<LoanInfo> findByCustom11(Integer min, Integer max);

    // regex
    // TODO: 17/9/18
    // @Query("{'name':/钱三/i}")
    // List<LoanInfo> findByCustom12();




}
