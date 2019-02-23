package by.intexsoft.scrzs.service;

import org.apache.zookeeper.KeeperException;

public interface ZKManager {

    /**
     * Create a Znode and save some data
     *
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void create(String path, byte[] data) throws KeeperException, InterruptedException;

    /**
     * Get ZNode Data
     *
     * @param path
     * @param watchFlag
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getZNodeData(String path, boolean watchFlag);

    /**
     * Update the ZNode Data
     *
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void update(String path, byte[] data) throws KeeperException, InterruptedException, KeeperException;
}
