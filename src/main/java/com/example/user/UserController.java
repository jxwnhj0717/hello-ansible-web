package com.example.user;

import com.example.zookeeper.LeaderListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private LeaderListener leaderListener;

    @PostMapping
    public void add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getInsertNode())) {
            user.setInsertNode(getNodeName());
        }
        repository.save(user);
    }

    private String getNodeName() {
        String prefix = leaderListener.isLeader() ? "master" : "slave";
        String ip = "";
        try {
            try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return prefix + ":" + ip;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {
        User user = repository.findById(id).get();
        user.setSelectNode(getNodeName());
        return user;
    }

    @GetMapping
    public List<User> list() {
        List<User> users = repository.findAll();
        for (User user : users) {
            user.setSelectNode(getNodeName());
        }
        return users;
    }

}
