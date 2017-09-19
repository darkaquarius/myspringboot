package boot.repository;

import boot.domain.IdfaStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huishen on 17/9/15.
 *
 */

public interface IdfaStatisticsRepository extends MongoRepository<IdfaStatistics, String> {



}
