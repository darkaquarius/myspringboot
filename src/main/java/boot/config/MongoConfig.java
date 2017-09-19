package boot.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by huishen on 17/9/15.
 *
 */

@Configuration
@EnableMongoRepositories(basePackages = "boot.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("mongo.database");
    }

    @Override
    public Mongo mongo() throws Exception {
        String host = env.getProperty("mongo.host");
        int port = Integer.parseInt(env.getProperty("mongo.port"));
        // String username = env.getProperty("mongo.username");
        // String password = env.getProperty("mongo.password");
        String database = env.getProperty("mongo.database");

        // MongoCredential credential = MongoCredential.createCredential(username,
        //     database, password.toCharArray());

        // return new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
        return new MongoClient(host, port);
    }
}
