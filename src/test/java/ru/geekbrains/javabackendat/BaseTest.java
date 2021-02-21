package ru.geekbrains.javabackendat;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class BaseTest {

    static Properties prop = new Properties();
    static String token;
    static final String INPUT_IMAGE_FILE_PATH = "white-moose-400x400.jpg";

    protected static RequestSpecification reqSpec;

    @BeforeAll
    static void beforeAll() {
        loadProperties();
        token = prop.getProperty("token");

        RestAssured.baseURI = prop.getProperty("base.url");
        RestAssured.filters(new AllureRestAssured());

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .build();
    }

    static void loadProperties() {

        try (InputStream file = new FileInputStream("src/test/resources/application.properties")) {
            prop.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    byte[] getFileContent() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource(INPUT_IMAGE_FILE_PATH)).getFile());

        byte[] fileContent = new byte[0];

        try {
            fileContent = FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
