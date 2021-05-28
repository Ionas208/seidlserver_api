package com.seidlserver.controller;

import com.seidlserver.cmd.CommandExecutor;
import com.seidlserver.cmd.Commands;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
    Created by: Jonas Seidl
    Date: 28.05.2021
    Time: 20:26
*/
@RestController
@RequestMapping("server")
public class GameserverController {
    @PostMapping(value = "/state")
    public ResponseEntity<String> stop(@RequestParam String script){
        try {
            String[] command = {script,"details"};
            String status = CommandExecutor.execute(command);

            status = findLine(status, "Status:");
            status = status.split(":")[1];
            status = status.replace(" ","");
            System.out.println(status);

            return ResponseEntity.ok(status);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    private String findLine(String line, String search){
        List<String> lines = line.lines().collect(Collectors.toList());
        for (String l: lines) {
            if(l.contains(search)){
                return l;
            }
        }
        return "";
    }
}
