package com.seidlserver.controller;

import com.seidlserver.cmd.CommandExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
    Created by: Jonas Seidl
    Date: 23.03.2021
    Time: 15:02
*/
@RestController
public class ServerController {
    @PostMapping(value = "/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public void stop(){
        try {
            CommandExecutor.execute("sudo shutdown -P now");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public void test(){
        try {
            String s = CommandExecutor.execute("ls");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
