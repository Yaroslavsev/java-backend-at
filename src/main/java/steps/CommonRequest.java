package steps;

import ru.geekbrains.javabackendat.dto.PostImageResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CommonRequest {

    static String INPUT_IMAGE_FILE_PATH = "https://cdnimg.rg.ru/i/gallery/2cff9087/1_a957b1d7.jpg";
    static String token = "Bearer 8ee9fd34f6a0b29546e4c7a6250cdadbe890e12f";

    public static PostImageResponse uploadCommonImage(){

       return   given()

                .header("Authorization", token)

                .multiPart("image", INPUT_IMAGE_FILE_PATH)

                .when()
                .post()

                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("data.id", is(notNullValue()))

                .extract()
                .body()
                .as(PostImageResponse.class);
    }

}
