package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Random;

@Service
public class NumberFactsService {
    private static final String API_HOST_HEADER = "x-rapidapi-host";
    private static final String API_HOST = "numbersapi.p.rapidapi.com";
    private static final String API_KEY_HEADER = "x-rapidapi-key";

    private static final String URI_FORMAT = "https://numbersapi.p.rapidapi.com/%d/trivia";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private final Random random = new Random();

    @Value("${hello.apiKey}")
    private String apiKey;

    public String randomNumberFact() {
        var num = random.nextInt(100);
        try {
            return Optional.of(httpClient.send(createRequest(num), HttpResponse.BodyHandlers.ofString()))
                    .filter(r -> r.statusCode() == 200)
                    .map(HttpResponse::body)
                    .orElseGet(() -> defaultFact(num));
        } catch (IOException | InterruptedException e) {
            return defaultFact(num);
        }
    }

    private String defaultFact(int num) {
        return String.format("%d is a number.", num);
    }

    private HttpRequest createRequest(int num) {
        return HttpRequest.newBuilder().uri(URI.create(String.format(URI_FORMAT, num)))
                .header(API_HOST_HEADER, API_HOST)
                .header(API_KEY_HEADER, apiKey)
                .GET()
                .build();
    }
}
