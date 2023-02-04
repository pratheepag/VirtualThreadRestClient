package org.example;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Instant begin = Instant.now();
        Random random = new Random();
        for(int i=1; i<10; i++){
            int todoId = random.nextInt(199)+1;

            Todo todo = Todo.readTodo(todoId);
            System.out.println(todo);
        }
        Instant end = Instant.now();
        System.out.println("Time Consumed="+ Duration.between(end, begin).abs().toSeconds()+"Seconds");
    }
}