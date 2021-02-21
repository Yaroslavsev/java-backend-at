package ru.geekbrains.javabackendat.utils;

import lombok.experimental.UtilityClass;

public enum listOfIdsForGetTests {
    VALID_ID ("veownGF"),
    EMPTY_ID (""),
    NOT_EXISTING_ID ("2123820");

    public String imageId;

    listOfIdsForGetTests(String imageId) {
        this.imageId = imageId;
    }
}
