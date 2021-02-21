package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import ru.geekbrains.javabackendat.utils.listOfIdsForGetTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@DisplayName("Тестирование метода GET")
public class GetImageTests extends BaseTest{

    @DisplayName("Получение изображения")
    @Test
    @EnumSource(listOfIdsForGetTests.class)
    void getImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .get(listOfIdsForGetTests.VALID_ID.imageId)
                .prettyPeek()

                .then()
                .body("data.id", is(listOfIdsForGetTests.VALID_ID.imageId))
                .statusCode(200)
                .body("success", is(true));
    }

    @DisplayName("Пустой запрос на получение изображения")
    @Test
    @EnumSource(listOfIdsForGetTests.class)
    void getEmptyRequestTest() {
        given()
                .spec(reqSpec)

                .when()
                .get(listOfIdsForGetTests.EMPTY_ID.imageId)
                .prettyPeek()

                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("data.error", containsString("An image ID is required for a GET request to /image"));
    }

    @DisplayName("Получение несуществующего изображения")
    @Test
    @EnumSource(listOfIdsForGetTests.class)
    void getNotExistingImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .get(listOfIdsForGetTests.NOT_EXISTING_ID.imageId)
                .prettyPeek()

                .then()
                .statusCode(404)
                .body(containsString("imgur: the simple 404 page"));
    }

}
