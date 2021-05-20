package com.seidlserver.controller;

import com.seidlserver.cmd.CommandExecutor;
import com.seidlserver.cmd.Commands;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 15:02
*/
@RestController
public class ServerController {
    @PostMapping(value = "/stop")
    public ResponseEntity stop(){
        try {
            CommandExecutor.execute(Commands.shutdown);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping(value = "/restart")
    public ResponseEntity restart(){
        try {
            CommandExecutor.execute(Commands.restart);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/memTotal")
    public ResponseEntity<Long> memTotal(){
        try {
            String s = CommandExecutor.execute(Commands.mem);
            s = findLine(s, "MemTotal");
            s = filterForNumbers(s);
            Long memTotal = Long.parseLong(s)/1_000_000;
            return ResponseEntity.ok(memTotal);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/memFree")
    public ResponseEntity<Long> memFree(){
        try {
            String s = CommandExecutor.execute(Commands.mem);
            s = findLine(s, "MemFree");
            System.out.println(s);
            s = filterForNumbers(s);
            Long memFree = Long.parseLong(s)/1_000_000;
            return ResponseEntity.ok(memFree);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/cpu")
    public ResponseEntity<String> mem(){
        try {
            String s = CommandExecutor.execute(Commands.cpu);
            return ResponseEntity.ok(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    private String filterForNumbers(String s ){
        return s.chars()
                .filter(ch -> Character.isDigit(ch))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
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

    public static void main(String[] args) {
    }
}
