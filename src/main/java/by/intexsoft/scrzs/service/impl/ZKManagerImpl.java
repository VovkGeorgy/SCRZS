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


@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ZKManagerImpl implements ZKManager {

    @Value("${zookeeper.host}")
    private String zookeeperHost;

    @Value("${zookeeper.session.timeout}")
    private int sessionTimeout;

    private static ZooKeeper zkeeper;
    private final CountDownLatch connectionLatch = new CountDownLatch(1);

    /**
     * Initialize connection
     */
    @PostConstruct
    private void createConnection() {
        try {
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

    @PreDestroy
    public void closeConnection() {
        try {
            zkeeper.close();
        } catch (InterruptedException e) {
            log.error("Cant close zookeeper connection, get error {}", e.getMessage());
        }
    }

    public void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getZNodeData(String path, boolean watchFlag) {
        try {
            byte[] b = zkeeper.getData(path, null, null);
            return new String(b, "UTF-8");
        } catch (Exception e) {
            log.error("Cant get zookeeper node, get error {}", e.getMessage());
        }
        return null;
    }

    public void update(String path, byte[] data) throws KeeperException, InterruptedException {
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }
}
