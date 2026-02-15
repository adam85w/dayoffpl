package net.adam85w.dayoff;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DayOffControllerTests {

    private static final String API = "/api/days-off/";

    @LocalServerPort
    private int port;

    private final RestTestClient restTestClient;

    DayOffControllerTests() {
        restTestClient = RestTestClient.bindToServer().build();
    }

    @Test
    void shouldBeNewYear() {
        LocalDate newYear = LocalDate.of(2024, 1, 1);
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", newYear))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2024, 1, 1), true, "New Year's Day");
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }

    @Test
    void shouldBeSaturday() {
        LocalDate saturday = LocalDate.of(2023, 11, 25); // saturday
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", saturday))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2023, 11, 25), true, "saturday");
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }

    @Test
    void shouldBeUnknownNameBecauseOfMissingTranslation() {
        LocalDate epiphany2022 = LocalDate.of(2022, 1, 6); // thursday - EPIPHANY
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", epiphany2022))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2022, 1, 6), true, "UNKNOWN");
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }

    @Test
    void shouldBeSundayInPolishLanguage() {
        LocalDate sunday = LocalDate.of(2023, 11, 26);
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}" + "?lang=pl", Collections.singletonMap("day", sunday))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2023, 11, 26), true, "niedziela");
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }

    @Test
    void shouldBeWorkingDay() {
        LocalDate monday = LocalDate.of(2023, 11, 6);
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", monday))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2023, 11, 6), false);
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }

    @Test
    void shouldBeEasternMonday() {
        LocalDate easternMonday = LocalDate.of(2013, 4, 1);
        EntityExchangeResult<DayOffResult> result = restTestClient.get()
                .uri("http://localhost:" + port + API + "{day}", Collections.singletonMap("day", easternMonday))
                .exchange()
                .expectBody(DayOffResult.class)
                .returnResult();
        var expectedResult = new DayOffResult(LocalDate.of(2013, 4, 1), true, "Easter Monday");
        Assertions.assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getResponseBody()).isEqualTo(expectedResult);
    }
}
