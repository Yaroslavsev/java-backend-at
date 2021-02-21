package ru.geekbrains.javabackendat;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekbrains.javabackendat.dto.PostImageResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static steps.CommonRequest.uploadCommonImage;

@DisplayName("Позитивное тестирование метода DELETE")
public class DeleteImagePositiveTests extends BaseTest {

    private static String uploadedImageId;
    private static String uploadedImageDeleteId;

    static ResponseSpecification postImageDeletePositiveResSpec;

    @BeforeEach
    void setUp() {
        PostImageResponse response = uploadCommonImage();

        uploadedImageId = response.getData().getId();
        uploadedImageDeleteId = response.getData().getDeletehash();

        postImageDeletePositiveResSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectBody("success", is(true))
                .build();
    }

    @DisplayName("Удаление изображения через ID")
    @Test
    void authedDeleteImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()

                .spec(postImageDeletePositiveResSpec);
    }

    @DisplayName("Удаление изображения через deleteID")
    @Test
    void unauthedDeleteImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .delete(uploadedImageDeleteId)
                .prettyPeek()

                .then()

                .spec(postImageDeletePositiveResSpec);
    }

}
