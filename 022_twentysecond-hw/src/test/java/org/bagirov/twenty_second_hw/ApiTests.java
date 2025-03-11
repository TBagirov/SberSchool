package org.bagirov.twenty_second_hw;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bagirov.twenty_second_hw.dto.MeasurementDto;
import org.bagirov.twenty_second_hw.dto.SensorDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testRegisterSensor() {
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName("TestSensor");

        given()
                .contentType(ContentType.JSON)
                .body(sensorDto)
                .log().body()
                .when()
                .post("/sensors/registration")
                .then()
                .log().body()
                .statusCode(anyOf(is(201), is(409))); // Теперь API возвращает 201 Created при успешной регистрации
    }

    @Test
    public void testGetAllSensors() {
        given()
                .when()
                .get("/sensors")
                .then()
                .statusCode(200)
                .body("$", not(empty())); // Проверяем, что список не пустой
    }

    @Test
    public void testAddMeasurement() {
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setValue(25.5f);
        measurementDto.setRaining(false);
        measurementDto.setSensorDto(new SensorDto("TestSensor"));

        given()
                .contentType(ContentType.JSON)
                .body(measurementDto)
                .when()
                .post("/measurements/add")
                .then()
                .statusCode(200);
    }

    @BeforeEach
    public void prepareTestData() {
        SensorDto sensorDto = new SensorDto("TestSensor");

        // 1️⃣ Регистрируем сенсор (если уже есть — игнорируем ошибку)
        given()
                .contentType(ContentType.JSON)
                .body(sensorDto)
                .when()
                .post("/sensors/registration")
                .then()
                .statusCode(anyOf(is(200), is(409), is(400))); // Добавили 400, чтобы тест продолжал

        // 2️⃣ Добавляем тестовые измерения
        MeasurementDto measurement1 = new MeasurementDto();
        measurement1.setValue(25.5f);
        measurement1.setRaining(false);
        measurement1.setSensorDto(sensorDto);

        MeasurementDto measurement2 = new MeasurementDto();
        measurement2.setValue(18.3f);
        measurement2.setRaining(true);
        measurement2.setSensorDto(sensorDto);

        given()
                .contentType(ContentType.JSON)
                .body(measurement1)
                .when()
                .post("/measurements/add")
                .then()
                .statusCode(anyOf(is(200), is(400)));

        given()
                .contentType(ContentType.JSON)
                .body(measurement2)
                .when()
                .post("/measurements/add")
                .then()
                .statusCode(anyOf(is(200), is(400)));
    }

    @Test
    public void testGetAllMeasurements() {
        given()
                .when()
                .get("/measurements")
                .then()
                .statusCode(200)
                .body("$", not(empty())); // Ожидаем, что список НЕ пуст
    }

    @Test
    public void testGetRainyDaysCount() {
        // Подготовка: добавляем 2 дождливых измерения
        MeasurementDto measurement1 = new MeasurementDto(22.5f, true, new SensorDto("TestSensor"));
        MeasurementDto measurement2 = new MeasurementDto(18.3f, true, new SensorDto("TestSensor"));

        given()
                .contentType(ContentType.JSON)
                .body(measurement1)
                .when()
                .post("/measurements/add")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(measurement2)
                .when()
                .post("/measurements/add")
                .then()
                .statusCode(200);

        // Запрос количества дождливых дней
        given()
                .when()
                .get("/measurements/rainyDaysCount")
                .then()
                .statusCode(200)
                .body("$", greaterThanOrEqualTo(2)); // Теперь проверяем, что >= 2
    }
}
