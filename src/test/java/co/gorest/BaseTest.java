package co.gorest;

import co.gorest.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseTest {

    @Test
    public void verifyUserId() throws URISyntaxException, IOException, InterruptedException {
        int userId = 5454462;
        URI exampleUri = new URI("https://gorest.co.in/public/v2/users/" + userId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        User userFromResponse;
        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            userFromResponse = objectMapper.readValue(response.body(), User.class);
            Assert.assertEquals(userFromResponse.getId(), userId, "UserId is not equal");
        }
        System.out.println(response.body());
    }

    @Test
    public void createNewUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User(1111111, "Jon4", "jon4@gmail.com", "female", "inactive");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);

        String token = "97042494f7fbceb7ccf2e263799a28a21ad5fe4b18e6d8b474b07e1a5c923b78";
        URI exampleUri = new URI("https://gorest.co.in/public/v2/users/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 201, "Status code is not equal");
    }

    @Test
    public void updateUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User();
        user.setName("Jon2");
        user.setEmail("Jon2@gmai.com");
        user.setStatus("active");
        user.setGender("female");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);

        String token = "97042494f7fbceb7ccf2e263799a28a21ad5fe4b18e6d8b474b07e1a5c923b78";
        URI exampleUri = new URI("https://gorest.co.in/public/v2/users/5456645");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "Status code is not equal");
    }

    @Test
    public void deleteUser() throws URISyntaxException, IOException, InterruptedException {
        String token = "97042494f7fbceb7ccf2e263799a28a21ad5fe4b18e6d8b474b07e1a5c923b78";
        URI exampleUri = new URI("https://gorest.co.in/public/v2/users/5456645");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .DELETE()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 204, "Status code is not equal");
    }
}
