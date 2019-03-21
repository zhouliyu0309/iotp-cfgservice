package com.cetiti.iotp.cfgservice.common.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CfgZkClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CfgZkClient.class);

    private ZooKeeper zooKeeper;
    private boolean isConnect = false;
    private static final int DEFAULT_TIME_OUT = 2000;

    @Value("${iot.cfg.zkClient.address}")
    private String address;

    @PostConstruct
    public void init(){
        if(zooKeeper == null){
            try {
                zooKeeper = new ZooKeeper(address, DEFAULT_TIME_OUT, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
                            isConnect = true;
                        }
                    }
                });
            } catch (IOException e) {
                LOGGER.error("zooKeeper create error");
            }
        }
    }

    /**
     * zookeeper是否连接
     * */
    public boolean isConnect(){
        return isConnect;
    }

    /**
     * 创建节点
     * */
    public String createNode(String path, byte[] data) throws KeeperException, InterruptedException {
        return this.zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    /**
     * 删除节点
     * */
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
    }

    /**
     * 获取节点
     *
     * */
    public Stat exists(String path) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path, false);
    }

    /**
     * 获取节点数据
     * */
    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getData(path, false, null);
    }

    /**
     * 更新节点数据
     * */
    public Stat setData(String path, byte[] data) throws KeeperException, InterruptedException {
        return zooKeeper.setData(path, data, -1);

    }

    /**
     * 关闭连接
     * */
    public void closeConnection() throws InterruptedException {
        if(zooKeeper != null){
            zooKeeper.close();
        }
    }

}