package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DeleteImageTests extends BaseTest {

    @Test
    void authedDeleteImageTest() {
        byte[] fileContent = getFileContent();
        String fileString = Base64.getEncoder().encodeToString(fileContent);

        String uploadedImageId = given()
                .headers("Authorization", token)

                .multiPart("image", fileString)

                .when()
                .post()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.id", is(notNullValue()))

                .extract()
                .response()
                .jsonPath()
                .getString("data.id");

        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    void unauthedDeleteImageTest() {
        byte[] fileContent = getFileContent();
        String fileString = Base64.getEncoder().encodeToString(fileContent);

        String uploadedImageDeleteId = given()
                .headers("Authorization", token)

                .multiPart("image", fileString)

                .when()
                .post()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.id", is(notNullValue()))

                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .delete(uploadedImageDeleteId)
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true));
    }

}
