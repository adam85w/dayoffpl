package net.adam85w.dayoff;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DayOffExceptionHandlerTests {

    private static final String API = "/api/days-off/";

    @LocalServerPort
    private int port;

    private final RestTestClient restTestClient;

    public DayOffExceptionHandlerTests() {
        restTestClient = RestTestClient.bindToServer().build();
    }

    @Test
    void shouldReturnBadRequestForInvalidDate() {
        EntityExchangeResult<String> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", "invalid-date"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(String.class)
                .returnResult();

        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getResponseBody()).contains("Invalid date format");
    }

    @Test
    void shouldReturnBadRequestForInvalidDateRange() {
        // Test with from date after to date
        EntityExchangeResult<String> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "range?from=2023-12-31&to=2023-01-01")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(String.class)
                .returnResult();

        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getResponseBody()).contains("Date from cannot be after date to");
    }

    @Test
    void shouldReturnBadRequestForYearsLimitationExceeded() {
        // Test with a range that exceeds the years limitation (default 100)
        EntityExchangeResult<String> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "range?from=2020-01-01&to=2121-01-01")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(String.class)
                .returnResult();

        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getResponseBody()).contains("Years limitation exceeded");
    }

    @Test
    void shouldReturnBadRequestForInvalidDateInDateList() {
        // Test with invalid date in list of dates
        EntityExchangeResult<String> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "dates?days=invalid-date")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody(String.class)
                .returnResult();

        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getResponseBody()).contains("Invalid date format");
    }
}