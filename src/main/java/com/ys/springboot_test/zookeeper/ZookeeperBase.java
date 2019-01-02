package com.ys.springboot_test.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperBase implements Watcher {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "192.168.16.128:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 2000;// ms
    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME,new ZookeeperBase());

        // 进行阻塞
        connectedSemaphore.await();

        System.out.println("zk连接成功..");
        // 创建父节点
        //zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 创建子节点
        // zk.create("/testRoot/children", "children data".getBytes(),
        // Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 获取节点信息
        /*Stat stat = new Stat();
        byte[] data = zk.getData("/testRoot", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        },stat);
        System.out.println(new String(data));*/
        // System.out.println(zk.getChildren("/testRoot", false));

        // 修改节点的值
        /*byte[] data = zk.getData("/testRoot", true, null);
        System.out.println(new String(data));
        zk.setData("/testRoot", "modify data root".getBytes(), -1,new AsyncCallback.StatCallback(){
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("StatCallback回调成功....路径为：" + path);
                System.out.println(ctx.toString());
            }
        },"ceshi");*/

        // 判断节点是否存在
        //System.out.println(zk.exists("/testRoot/children", false));
        // 删除节点
        /*zk.delete("/testRoot/children", -1);
        System.out.println(zk.exists("/testRoot/children", true));*/

        //自定义权限
        List<ACL> acls = new ArrayList<ACL>();  // 权限列表
        // 第一个参数是权限scheme，第二个参数是加密后的用户名和密码
        Id user1 = new Id("digest", AclUtils.getDigestUserPwd("user1:123456"));
        Id user2 = new Id("digest", AclUtils.getDigestUserPwd("user2:123456"));
        acls.add(new ACL(ZooDefs.Perms.ALL, user1));  // 给予所有权限
        acls.add(new ACL(ZooDefs.Perms.READ, user2));  // 只给予读权限
        acls.add(new ACL(ZooDefs.Perms.DELETE | ZooDefs.Perms.CREATE, user2));  // 多个权限的给予方式，使用 | 位运算符
        /*String result = zk.create("/testDigestNode", "test data".getBytes(), acls, CreateMode.PERSISTENT);
        if (result != null) {
            System.out.println("创建节点：\t" + result + "\t成功...");
        }*/
        zk.addAuthInfo("digest", "user2:123456".getBytes());
        byte[] data = zk.getData("/testDigestNode", true,null);
        System.out.println(new String(data));

        zk.close();

    }

    @Override
    public void process(WatchedEvent event) {
        // 获取事件的状态
        Event.KeeperState keeperState = event.getState();
        Event.EventType eventType = event.getType();
        // 如果是建立连接
        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                System.out.println("zk 建立连接");
                connectedSemaphore.countDown();
            }
        }
        System.out.println("watch事件执行~~~~");
    }
}
