package com.seidlserver.controller;

import com.seidlserver.cmd.CommandExecutor;
import com.seidlserver.cmd.Commands;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
    Created by: Jonas Seidl
    Date: 28.05.2021
    Time: 20:26
*/
@RestController
@RequestMapping("gameserver")
public class GameserverController {
    @GetMapping(value = "/state")
    public ResponseEntity<String> state(@RequestParam String script){
        try {
            script = "/home/"+script+"/"+script;
            String[] command = {script,"details"};
            String status = CommandExecutor.execute(command);
            status = findLine(status, "Status:");
            status = status.split(":")[1];
            status = status.replace(" ","");
            status = status.replace("\u001B[31m","");
            status = status.replace("\u001B[0m","");
            return ResponseEntity.ok(status);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping(value = "/start")
    public ResponseEntity<String> start(@RequestParam String script){
        try {
            script = "/home/"+script+"/"+script;
            String[] command = {script,"start"};
            CommandExecutor.execute(command);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping(value = "/stop")
    public ResponseEntity<String> stop(@RequestParam String script){
        try {
            script = "/home/"+script+"/"+script;
            String[] command = {script,"stop"};
            CommandExecutor.execute(command);
            return ResponseEntity.ok().build();
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
