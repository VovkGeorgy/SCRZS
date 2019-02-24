package by.intexsoft.scrzs.service;

import org.apache.zookeeper.KeeperException;

/**
 * Zookeeper manager interface
 */
public interface ZKManager {

    /**
     * Method create znode on zk server
     *
     * @param path node tree path
     * @param data data to node
     * @throws KeeperException      ex
     * @throws InterruptedException ex
     */
    public void create(String path, byte[] data) throws KeeperException, InterruptedException;

    /**
     * Method get znode data from zk server
     *
     * @param path      tree path to znode
     * @param watchFlag watch flag
     * @return znode data
     */
    public String getZNodeData(String path, boolean watchFlag);

    /**
     * Method update znode data on zk server
     *
     * @param path tree path to znode
     * @param data new data to node
     * @throws KeeperException      ex
     * @throws InterruptedException ex
     */
    public void update(String path, byte[] data) throws KeeperException, InterruptedException, KeeperException;
}
