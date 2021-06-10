package com.seidlserver.controller;

import com.seidlserver.cmd.CommandExecutor;
import com.seidlserver.cmd.Commands;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

/***
 * Controller for server related requests
 * Reachable under /server/
 */
@RestController
@RequestMapping("server")
public class ServerController {

    /***
     * Entrypoint for stopping the server
     * @return ResponseEntity with Code
     *         200 OK: When the stopping was successful
     *         500 INTERNAL SERVER ERROR: When there is some other error
     * Note: If successful, there will be no response, because the server will instantly shut down
     */
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

    /***
     * Entrypoint for restarting the server
     * @return ResponseEntity with Code
     *         200 OK: When the restarting was successful
     *         500 INTERNAL SERVER ERROR: When there is some other error
     * Note: If successful, there will be no response, because the server will instantly shut down
     */
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

    /***
     * Entrypoint for getting the total available memory
     * @return ResponseEntity with Code
     *         200 OK: When the fetching of the memory was successful
     *                 Available memory is included in response body
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
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

    /***
     * Entrypoint for getting the free memory
     * @return ResponseEntity with Code
     *         200 OK: When the fetching of the memory was successful
     *                 Free memory is included in response body
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
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

    /***
     * Entrypoint for getting the current CPU Usage
     * @return ResponseEntity with Code
     *         200 OK: When the fetching of the cpu usage was successful
     *                 Output of mpstat -o Command is included in response body for further furnishing
     *         500 INTERNAL SERVER ERROR: When there is some other error
     */
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

    /***
     * Filters all numbers from a string
     * @param s The String to filter from
     * @return The string without numbers
     */
    private String filterForNumbers(String s ){
        return s.chars()
                .filter(ch -> Character.isDigit(ch))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
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
