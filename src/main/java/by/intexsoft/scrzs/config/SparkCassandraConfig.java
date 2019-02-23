package by.intexsoft.scrzs.config;

import by.intexsoft.scrzs.service.ZKManager;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.cassandra.CassandraSQLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        host = zkManager.getZNodeData("/spark_cassandra_connection_host", false);
        deployMode = zkManager.getZNodeData("/spark_submit_deployMode", false);
        appName = zkManager.getZNodeData("/spark_app_name", false);
        maxCores = zkManager.getZNodeData("/spark_cores_max", false);
        masterUrl = zkManager.getZNodeData("/spark_master_url", false);
        contexJar = zkManager.getZNodeData("/spark_context_jar", false);
        keyspace = zkManager.getZNodeData("/cassandra_keyspace", false);
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        SparkConf conf = new SparkConf(true)
                .set("spark.cassandra.connection.host", host)
                .set("spark.submit.deployMode", deployMode);
        conf.setAppName(appName);
        conf.set("spark.cores.max", maxCores);
        conf.setMaster(masterUrl);
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.addJar(contexJar);
        return sc;
    }

    @Bean
    public CassandraSQLContext sqlContext() {
        CassandraSQLContext cassandraSQLContext = new CassandraSQLContext(javaSparkContext().sc());
        cassandraSQLContext.setKeyspace(keyspace);
        return cassandraSQLContext;
    }
}
