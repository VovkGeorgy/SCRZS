package by.intexsoft.scrzs.service;

import by.intexsoft.scrzs.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;

/**
 * Cassandra user service with crud operations
 */
@Slf4j
@Service
public class CassandraUserService {

    private final JavaSparkContext javaSparkContext;
    private final String keyspace;


    @Autowired
    public CassandraUserService(JavaSparkContext javaSparkContext, ZKManager zkManager) {
        this.javaSparkContext = javaSparkContext;
        keyspace = zkManager.getZNodeData("/Spark/Cassandra/keyspace", false);

    }

    /**
     * Method get user from cassandra by user id
     *
     * @param userId id of needed user
     */
    void getUserById(Long userId) {
        log.info("Get user by id - {}", userId);
        JavaRDD<User> usersNameById = javaFunctions(javaSparkContext).cassandraTable(keyspace,
                "user", mapRowTo(User.class)).where("userId=?", userId);
        for (User user : usersNameById.collect()) {
            System.out.println("\nUser firstname is - " + user.toString() + "\n");
        }
    }

    /**
     * Mthod save user instance to cassandra
     *
     * @param user instance for saving
     */
    void saveUser(User user) {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        log.info("Save user with id {} in base", user.getUserid());
        JavaRDD<User> userInfoJavaRDD = javaSparkContext.parallelize(userList);
        javaFunctions(userInfoJavaRDD).writerBuilder(keyspace, "user", mapToRow(User.class)).saveToCassandra();
        System.out.println("\n Entity saved \n");
    }
}
