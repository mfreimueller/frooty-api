package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.payload.request.PredictionRequest;
import com.mfreimueller.frooty.payload.response.PredictionResponse;
import com.mfreimueller.frooty.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zeromq.ZMQ;

import java.security.Principal;

@RestController
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ZMQ.Socket socket;

    @PostMapping
    public PredictionResponse predictWeek(Principal principal, @RequestBody PredictionRequest request) {
        final Group group = groupRepository.findById(request.groupId())
                // TODO: find better alternative to u.getUsername() -> duplicated name???
                .filter(g -> g.getUsers().stream().anyMatch(u -> u.getUsername().equals(principal.getName())))
                .orElseThrow();

        boolean success = socket.send("{ \"group_id\": %d }".formatted(group.getId()));
        assert success : "ZeroMQ socket returned false on .send()!";

        String response = null;
        do {
            response = socket.recvStr();
        } while (response == null);

        // TODO: convert to JSON
        Integer[][] mealPredictions = null;

        return new PredictionResponse((mealPredictions));
    }
}
