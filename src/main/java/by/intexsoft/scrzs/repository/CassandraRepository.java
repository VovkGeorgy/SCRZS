package by.intexsoft.scrzs.repository;

import org.apache.spark.sql.DataFrame;
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

    public Long getUserRowsCount() {
        DataFrame user = cassandraSQLContext.sql("select * from user");
        System.out.println("\nFrom repository\n");
        return user.count();
    }
}
