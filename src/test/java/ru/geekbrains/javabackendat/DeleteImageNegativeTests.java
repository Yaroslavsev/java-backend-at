package ru.geekbrains.javabackendat;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekbrains.javabackendat.dto.PostImageResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static steps.CommonRequest.uploadCommonImage;

@DisplayName("Негативное тестирование метода DELETE")
public class DeleteImageNegativeTests extends BaseTest {

    private static String uploadedImageId;

    static ResponseSpecification postImageDeleteNegativeResSpec;

    @BeforeEach
    void setUp() {
        PostImageResponse response = uploadCommonImage();

        uploadedImageId = response.getData().getId();

        given()
                .spec(reqSpec)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()
                .statusCode(200);

        postImageDeleteNegativeResSpec = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectBody(containsString("imgur: the simple 404 page"))
                .build();
    }

    @DisplayName("Удаление только что удаленного изображения")
    @Test
    void DeleteJustDeletedImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .delete(uploadedImageId)
                .prettyPeek()

                .then()

                .spec(postImageDeleteNegativeResSpec);
    }

    @DisplayName("Удаление несуществующего изображения")
    @Test
    void DeleteNotExistingImageTest() {
        given()
                .spec(reqSpec)

                .when()
                .delete(prop.getProperty("notExistingDeleteId"))
                .prettyPeek()

                .then()

                .spec(postImageDeleteNegativeResSpec);
    }

}
