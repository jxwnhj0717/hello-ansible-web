package com.example.zookeeper;

import lombok.Getter;
import lombok.Value;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.event.OnFailedToAcquireMutexEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LeaderListener {

    private boolean leader = false;

    @EventListener
    public void onGrantedEvent(OnGrantedEvent event) {
        leader = true;
    }

    @EventListener
    public void onRevokedEvent(OnRevokedEvent event) {
        leader = false;
    }

    @EventListener
    public void onFailedToAcquireMutexEvent(OnFailedToAcquireMutexEvent event) {
        leader = false;
    }

}