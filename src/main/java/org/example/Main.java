package org.example;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Instant begin = Instant.now();
        for(int i=1; i<5; i++){
            Todo todo = Todo.readTodo(i);
            System.out.println(todo);
        }
        Instant end = Instant.now();
        System.out.println("Hello world!");
    }
}