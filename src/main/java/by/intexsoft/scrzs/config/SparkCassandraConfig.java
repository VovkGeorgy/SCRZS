package by.intexsoft.scrzs.config;

import by.intexsoft.scrzs.service.ZKManager;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.cassandra.CassandraSQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spark Cassandra configuration class
 */
@Configuration
public class SparkCassandraConfig {

    private final String host;
    private final String deployMode;
    private final String appName;
    private final String maxCores;
    private final String masterUrl;
    private final String contexJar;
    private final String keyspace;

    @Autowired
    public SparkCassandraConfig(ZKManager zkManager) {
        host = zkManager.getZNodeData("/Spark/Cassandra/connection_host", false);
        keyspace = zkManager.getZNodeData("/Spark/Cassandra/keyspace", false);
        deployMode = zkManager.getZNodeData("/Spark/submit_deployMode", false);
        appName = zkManager.getZNodeData("/Spark/app_name", false);
        maxCores = zkManager.getZNodeData("/Spark/cores_max", false);
        masterUrl = zkManager.getZNodeData("/Spark/master_url", false);
        contexJar = zkManager.getZNodeData("/Spark/context_jar", false);
    }

    /**
     * Create and config spark java context for cassandra service
     *
     * @return
     */
    @Bean
    public JavaSparkContext javaSparkContext() {
        SparkConf conf = new SparkConf(true)
                .set("spark.cassandra.connection.host", host)
                .set("spark.submit.deployMode", deployMode);
        conf.setAppName(appName);
        conf.set("spark.cores.max", maxCores);
        conf.setMaster(masterUrl);
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        javaSparkContext.addJar(contexJar);
        return javaSparkContext;
    }

    /**
     * Create Cassandra sql context
     *
     * @return cassandra sql context entity
     */
    @Bean
    public CassandraSQLContext sqlContext() {
        CassandraSQLContext cassandraSQLContext = new CassandraSQLContext(javaSparkContext().sc());
        cassandraSQLContext.setKeyspace(keyspace);
        return cassandraSQLContext;
    }
}
