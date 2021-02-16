package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class PostImageTests extends BaseTest {

    private String uploadedImageId;
    private String fileString;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContent();
        fileString = Base64.getEncoder().encodeToString(fileContent);
    }

    @Test
    void postImageBase64Test() {
        uploadedImageId = given()
                .log()
                .all()
                .headers("Authorization", token)

                .multiPart("image", fileString)

                .when()
                .post()
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.account_id", equalTo(145270179))
                .body("data.id", is(notNullValue()))

                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    void postImageURLTest() {
        uploadedImageId = given()
                .log()
                .all()
                .headers("Authorization", token)

                .multiPart("image", "https://ru.sweden.se/wp-content/uploads/2019/01/white-moose-400x400.jpg")

                .when()
                .post()
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.account_id", equalTo(145270179))
                .body("data.id", is(notNullValue()))

                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200);
    }

}
