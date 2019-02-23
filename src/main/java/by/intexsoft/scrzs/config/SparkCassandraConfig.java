package by.intexsoft.scrzs.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.cassandra.CassandraSQLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkCassandraConfig {

    @Bean
    public JavaSparkContext javaSparkContext() {
        SparkConf conf = new SparkConf(true)
                .set("spark.cassandra.connection.host", "192.168.99.100")
                .set("spark.submit.deployMode", "client");
        conf.setAppName("mysparkapp");
        conf.set("spark.cores.max", "2");
        conf.setMaster("spark://192.168.99.100:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.addJar("./target/scrzs-1.0-SNAPSHOT.jar");
        return sc;
    }

    @Bean
    public CassandraSQLContext sqlContext() {
        CassandraSQLContext cassandraSQLContext = new CassandraSQLContext(javaSparkContext().sc());
        cassandraSQLContext.setKeyspace("mykeyspace");
        return cassandraSQLContext;
    }
}
