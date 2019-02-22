package by.intexsoft.scrzs.service;

import by.intexsoft.scrzs.repository.CassandraRepository;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassandraService {

    private final CassandraRepository cassandraRepository;
    private final JavaSparkContext javaSparkContext;

    @Autowired
    public CassandraService(CassandraRepository cassandraRepository, JavaSparkContext javaSparkContext) {
        this.cassandraRepository = cassandraRepository;
        this.javaSparkContext = javaSparkContext;
    }

    public void getUserData(){
        Long count = cassandraRepository.getUserRowsCount();
        System.out.println("this is count of users" + count);
//        javaSparkContext.sc().stop();
    }
}
