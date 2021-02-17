package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("Тестирование метода GET")
public class GetImageTests extends BaseTest{

    @DisplayName("Получение изображения")
    @Test
    void getImageTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .get("veownGF")
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.id", is("veownGF"));
    }

    @DisplayName("Пустой запрос на получение изображения")
    @Test
    void getEmptyRequestTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .get()
                .prettyPeek()

                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("data.error", containsString("An image ID is required for a GET request to /image"));
    }

    @DisplayName("Получение несуществующего изображения")
    @Test
    void getNotExistingImageTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .get("2123820")
                .prettyPeek()

                .then()
                .statusCode(404)
                .body(containsString("imgur: the simple 404 page"));
    }

}
