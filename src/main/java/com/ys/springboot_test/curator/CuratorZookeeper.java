package com.ys.springboot_test.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

public class CuratorZookeeper {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);//刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.16.128:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)//连接超时时间
                .retryPolicy(retryPolicy)
                .namespace("curator")
                .authorization("digest","root:root".getBytes())//权限访问
                .build();
        client.start();

        /*client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/test","init".getBytes());*/

        /*List<String> list = client.getChildren().forPath("/");
        System.out.println(list);*/

        Stat stat = new Stat();
        String re = new String(client.getData().storingStatIn(stat)//在获取节点内容的同时把状态信息存入Stat对象
                .forPath("/test/c"));
        System.out.println(re);
        System.out.println(stat);

        /*client.delete().guaranteed()//保障机制，若未删除成功，只要会话有效会在后台一直尝试删除
                .deletingChildrenIfNeeded()//若当前节点包含子节点
                .withVersion(-1)//指定版本号
                .forPath("/");*/

        /*client.setData().withVersion(-1)//修改前获取一次节点数据得到版本信息
                .forPath("/test", "111".getBytes());*/

        /*final NodeCache cache = new NodeCache(client,"/test",true);
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] res = cache.getCurrentData().getData();
                System.out.println("data: " + new String(res));
            }
        });
        Thread.sleep(Integer.MAX_VALUE);*/

        //子节点监听
        /*@SuppressWarnings("resource")
        final PathChildrenCache cache = new PathChildrenCache(client,"/test",true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curator, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("add:" + event.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("update:" + event.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("remove:" + event.getData());
                        break;
                    default:
                        break;
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);*/

        /*ACL aclRoot = new ACL(ZooDefs.Perms.ALL,new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
        List<ACL> aclList = new ArrayList<ACL>();
        aclList.add(aclRoot);

        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .withACL(aclList)
                .forPath("/test/c","test".getBytes());
        System.out.println(path);*/

    }
}
