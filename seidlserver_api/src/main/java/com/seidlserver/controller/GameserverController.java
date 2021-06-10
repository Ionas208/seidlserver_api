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

/***
 * Controller for gameserver related requests
 * Reachable under /gameserver/
 */
@RestController
@RequestMapping("gameserver")
public class GameserverController {

    /***
     * Entrypoint for getting the state of a gameserver
     * @param linuxuser The linux user under which the gameserver is located
     * @param script The name of the lgsm script
     * @return ResponseEntity with Code
     *         200 OK: When the fetching of the state was successful
     *                 State is included in response body
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
    @GetMapping(value = "/state")
    public ResponseEntity<String> state(@RequestParam String linuxuser, @RequestParam String script){
        try {
            script = "/home/"+linuxuser+"/"+script;
            String[] command = {script,"details"};
            String status = CommandExecutor.execute(command);
            status = findLine(status, "Status:");
            status = status.split(":")[1];
            status = status.replace(" ","");
            status = status.replace("\u001B[31m","");
            status = status.replace("\u001B[0m","");
            status = status.replace("\u001B[32m","");
            return ResponseEntity.ok(status);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    /***
     * Entrypoint for starting a gameserver
     * @param linuxuser The linux user under which the gameserver is located
     * @param script The name of the lgsm script
     * @return ResponseEntity with Code
     *         200 OK: When the starting was successful
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
    @PostMapping(value = "/start")
    public ResponseEntity<String> start(@RequestParam String linuxuser, @RequestParam String script){
        try {
            script = "/home/"+linuxuser+"/"+script;
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

    /***
     * Entrypoint for stopping a gameserver
     * @param linuxuser The linux user under which the gameserver is located
     * @param script The name of the lgsm script
     * @return ResponseEntity with Code
     *         200 OK: When the stopping was successful
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
    @PostMapping(value = "/stop")
    public ResponseEntity<String> stop(@RequestParam String linuxuser, @RequestParam String script){
        try {
            script = "/home/"+linuxuser+"/"+script;
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


    /***
     * Finds the line where a certain key word is contained
     * @param line The lines to search through
     * @param search The search key word
     * @return The line where the search key word was found
     */
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
