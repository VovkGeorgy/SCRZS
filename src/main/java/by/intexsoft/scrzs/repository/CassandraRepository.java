package by.intexsoft.scrzs.repository;

import org.apache.spark.sql.cassandra.CassandraSQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CassandraRepository {

    private final CassandraSQLContext cassandraSQLContext;

    @Autowired
    public CassandraRepository(CassandraSQLContext cassandraSQLContext) {
        this.cassandraSQLContext = cassandraSQLContext;
    }

    public void deleteUserById(Long userId) {
        System.out.println("\nFrom delete method\n");
        String query = "delete from user where userid=" + userId + ";";
        cassandraSQLContext.sql(query);
        System.out.println("\nUser are deleted!\n");
    }
}
