package ru.geekbrains.javabackendat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class PostImageUpdateTests extends BaseTest {

    private static String uploadedImageId;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContent();
        String fileString = Base64.getEncoder().encodeToString(fileContent);

        uploadedImageId = given()

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
    }

    @Test
    void postImageParamsUpdateTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .multiPart("title", "Test moose title")
                .multiPart("description", "Test moose long and meaningless description")

                .when()
                .post(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    void postImageFavoriteUpdateTest() {
        given()
                .log()
                .all()
                .headers("Authorization", token)

                .when()
                .post("{uploadedImageId}/favorite", uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data", equalTo("favorited"));
    }

    @AfterAll
    static void afterAll() {
        given()
                .headers("Authorization", token)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200);
    }

}
