package ru.geekbrains.javabackendat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostImageResponse extends CommonResponse <PostImageResponse.ImageData> {

    @Data
    public static class ImageData {

        private String id;
        private String title;
        private String description;
        private Integer datetime;
        private String type;
        private Boolean animated;
        private Integer width;
        private Integer height;
        private Integer size;
        private Integer views;
        private Integer bandwidth;
        private String vote;
        private Boolean favorite;
        private String nsfw;
        private String section;
        @JsonProperty("account_url")
        private String accountUrl;
        @JsonProperty("account_id")
        private Integer accountId;
        @JsonProperty("is_ad")
        private Boolean isAd;
        @JsonProperty("in_most_viral")
        private Boolean inMostViral;
        @JsonProperty("has_sound")
        private Boolean hasSound;
        private List<String> tags = new ArrayList<>();
        @JsonProperty("ad_type")
        private Integer adType;
        @JsonProperty("ad_url")
        private String adUrl;
        private String edited;
        @JsonProperty("in_gallery")
        private Boolean inGallery;
        private String deletehash;
        private String name;
        private String link;
    }

}
