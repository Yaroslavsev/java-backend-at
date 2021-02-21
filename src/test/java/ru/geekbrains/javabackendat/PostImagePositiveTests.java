package ru.geekbrains.javabackendat;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekbrains.javabackendat.dto.PostImageResponse;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Позитивное тестирование метода POST")
public class PostImagePositiveTests extends BaseTest {

    private String uploadedImageId;
    private String fileString;

    static ResponseSpecification postImagePositiveResSpec;

    @BeforeEach
    void setUp() {
        byte[] fileContent = getFileContent();
        fileString = Base64.getEncoder().encodeToString(fileContent);

        postImagePositiveResSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectBody("data.account_id", equalTo(145270179))
                .expectBody("data.id", is(notNullValue()))
                .build();
    }

    @DisplayName("Загрузка изображения через base64")
    @Test
    void postImageBase64Test() {
        PostImageResponse response = given()
                .spec(reqSpec)

                .multiPart("image", fileString)

                .when()
                .post()
                .prettyPeek()

                .then()

                .spec(postImagePositiveResSpec)

                .extract()
                .body()
                .as(PostImageResponse.class);

        uploadedImageId = response.getData().getId();
    }

    @DisplayName("Загрузка изображения через URL")
    @Test
    void postImageURLTest() {
        PostImageResponse response = given()
                .spec(reqSpec)

                .multiPart("image", prop.getProperty("imageUrl"))

                .when()
                .post()
                .prettyPeek()

                .then()

                .spec(postImagePositiveResSpec)

                .extract()
                .body()
                .as(PostImageResponse.class);

        uploadedImageId = response.getData().getId();
    }

    @AfterEach
    void tearDown() {
        given()
                .spec(reqSpec)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200);
    }
}
