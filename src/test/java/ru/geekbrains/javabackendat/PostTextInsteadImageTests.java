package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("Отправка невалидных данных методом POST")
public class PostTextInsteadImageTests extends BaseTest {

    @DisplayName("Отправка текста вместо изображения")
    @Test
    void postTextInsteadImageTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .multiPart("image", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque imperdiet.")

                .when()
                .post()
                .prettyPeek()

                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("data.error", containsString("Invalid URL"));
    }

}
