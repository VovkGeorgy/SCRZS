package by.intexsoft.scrzs.service.impl;

import by.intexsoft.scrzs.service.ZKManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper manager class with connection creation and simple operation with znodes
 */
@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ZKManagerImpl implements ZKManager {

    @Value("${zookeeper.host}")
    private String zookeeperHost;

    @Value("${zookeeper.session.timeout}")
    private int sessionTimeout;

    @Value("${zookeeper.string.data.encoding}")
    private String dataEncoding;

    private static ZooKeeper zkeeper;
    private final CountDownLatch connectionLatch = new CountDownLatch(1);

    /**
     * Method initialize zk connection
     */
    @PostConstruct
    private void createConnection() {
        try {
            log.info("Zookeeper create connection to {} host", zookeeperHost);
            zkeeper = new ZooKeeper(zookeeperHost, sessionTimeout, watcher -> {
                if (watcher.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    connectionLatch.countDown();
                }
            });
            connectionLatch.await();
        } catch (Exception e) {
            log.error("Cant create zookeeper connection, get error {}", e.getMessage());
        }
    }

    /**
     * Method close zk connection
     */
    @PreDestroy
    public void closeConnection() {
        try {
            zkeeper.close();
        } catch (InterruptedException e) {
            log.error("Cant close zookeeper connection, get error {}", e.getMessage());
        }
    }

    /**
     * Method create znode on zk server
     *
     * @param path node tree path
     * @param data data to node
     * @throws KeeperException      ex
     * @throws InterruptedException ex
     */
    @Override
    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        log.info("Create zookeeper node with {} path", path);
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * Method get znode data from zk server
     *
     * @param path      tree path to znode
     * @param watchFlag watch flag
     * @return znode data
     */
    @Override
    public String getZNodeData(String path, boolean watchFlag) {
        try {
            log.info("Get zookeeper data from znode {} path", path);
            byte[] zkeeperData = zkeeper.getData(path, null, null);
            return new String(zkeeperData, dataEncoding);
        } catch (Exception e) {
            log.error("Cant get zookeeper node, get error {}", e.getMessage());
        }
        return null;
    }

    /**
     * Method update znode data on zk server
     *
     * @param path tree path to znode
     * @param data new data to node
     * @throws KeeperException      ex
     * @throws InterruptedException ex
     */
    @Override
    public void update(String path, byte[] data) throws KeeperException, InterruptedException {
        log.info("Update zookeeper node data from path {}", path);
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }
}
