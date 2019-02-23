package by.intexsoft.scrzs.service;

import by.intexsoft.scrzs.model.User;
import by.intexsoft.scrzs.repository.CassandraRepository;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapColumnTo;

@Service
public class CassandraService {

    private final CassandraRepository cassandraRepository;
    private final JavaSparkContext javaSparkContext;

    @Autowired
    public CassandraService(CassandraRepository cassandraRepository, JavaSparkContext javaSparkContext) {
        this.cassandraRepository = cassandraRepository;
        this.javaSparkContext = javaSparkContext;
    }

    public void getUserData() {
        System.out.println("\n Service start \n");
        JavaRDD<String> updatedOnDay = javaFunctions(javaSparkContext).cassandraTable("mykeyspace",
                "user", mapColumnTo(String.class)).select("username");
        System.out.println("\n Start printing usernames \n");
        for (String user : updatedOnDay.collect()) {
            System.out.println(user);
        }

        Long count = cassandraRepository.getUserRowsCount();
        System.out.println("this is count of users" + count);
    }
}
