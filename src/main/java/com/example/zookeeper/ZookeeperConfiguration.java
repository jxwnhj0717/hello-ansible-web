package com.example.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;

import javax.annotation.PostConstruct;

@Configuration
public class ZookeeperConfiguration {

//    @Autowired
//    private LeaderInitiatorFactoryBean leaderInitiatorFactoryBean;
//
//    @PostConstruct
//    private void init() {
//        leaderInitiatorFactoryBean.getObject().start();
//    }

    @Bean
    public ZookeeperProperties getZookeeperProperties() {
        return new ZookeeperProperties();
    }

    @Bean
    public CuratorFrameworkFactoryBean getCuratorFrameworkFactoryBean(ZookeeperProperties properties) {
        return new CuratorFrameworkFactoryBean(properties.getConnectString());
    }


    @Bean
    public LeaderInitiatorFactoryBean leaderInitiator(CuratorFramework client, ZookeeperProperties properties) {
        return new LeaderInitiatorFactoryBean()
                .setClient(client)
                .setPath(properties.getLeaderPath())
                .setRole("leader");
    }

}