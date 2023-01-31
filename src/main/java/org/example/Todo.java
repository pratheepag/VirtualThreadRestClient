package org.example;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonParser;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Future;

public record Todo(String userId, String id, String title, Boolean completed ){
    public static Todo readTodo(Integer index) throws IOException, InterruptedException {
        try(var scope = new StructuredTaskScope<Todo>()){
            Future<Todo> futureA = scope.fork(()-> Todo.readTodoFromSource(index));
            scope.join();
            Todo todoA = futureA.resultNow();
            return todoA;
        }
    }
    public static Todo fromJson(String json){
        try(JsonParser parser = Json.createParser(new StringReader(json))){
            parser.next();
            JsonObject jsonObject = parser.getObject();
            String userId = jsonObject.get("userId").toString();
            String id = jsonObject.get("id").toString();
            String title = jsonObject.get("title").toString();
            Boolean completed = jsonObject.get("completed").equals(Boolean.TRUE);
            return new Todo(userId, id, title, completed);
        }
    }
    public static Todo readTodoFromSource(Integer index) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/todos/"+index))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200){
            String json = response.body();
            Todo todo = Todo.fromJson(json);
            return todo;
        }else{
            throw new RuntimeException("Server unavailable");
        }
    }
}
