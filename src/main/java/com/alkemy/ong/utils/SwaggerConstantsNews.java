package com.alkemy.ong.utils;


public class SwaggerConstantsNews {

    public static final String MODEL_NEW = "{\"id\" : \"1\",\n \"name\" : \"final exam\",\n \"content\" : \"the final exam will be in 30 days\",\n \"image\" : \"imageUrl\",\n  \"creationDate\": \"2022-04-10T20:48:18.617Z\",\n" + "  \"updateDate\": \"2022-04-10T20:48:18.618Z\"\n}";

    public static final String MODEL_NEW_CREATED = "{\n" + "  \"message\": \"The new was created successfully.\",\n" + "  \"status_code\": 200,\n" + "  \"path\": \"/news\"\n" + "}";

    public static final String MODEL_NEW_SAVED = "{\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"nameOfTheNew\",\n" +
            "    \"content\": \"contentOfTheNew\",\n" +
            "    \"image\": \"imageUrl\",\n" +
            "    \"categoryId\": {\n" +
            "        \"idCategories\": 1,\n" +
            "        \"name\": \"nameOfTheCategory\",\n" +
            "        \"description\": \"descOfTheCategory\",\n" +
            "        \"image\": \"imageURL\",\n" +
            "        \"deleted\": false,\n" +
            "        \"creationDate\": \"10-04-2022 00:00:00\",\n" +
            "        \"updateDate\": \"10-04-2022 00:00:00\"\n" +
            "    },\n" +
            "    \"deleted\": false,\n" +
            "    \"creationDate\": \"10-04-2022 21:36:45\",\n" +
            "    \"updateDate\": \"10-04-2022 22:16:43\"\n" +
            "}";

    public static final String MODEL_NEW_PARAM_ERROR = "{\n" + "  \"message\": {\n" + " \"image\": \"Image cannot be null\"" + "  },\n" + "  \"status_code\": 400,\n" + "  \"path\": \"/news\"\n" + "}";

    public static final String MODEL_NEW_FOUND = MODEL_NEW;

    public static final String MODEL_NEW_NOT_FOUND = "{\n" + "  \"message\": \"The new has been not found.\", \n" + "  \"status_code\": 404,\n" + "  \"path\": \"/news/{id}\"\n" + "}";

    public static final String MODEL_NEW_DELETED = "{\n" + "  \"message\": \"News delete ok.\" \n" + "}";

    public static final String MODEL_NEW_LIST_FOUND = "{\n" + "    \"content\": [ \n" + MODEL_NEW + ",\n" + MODEL_NEW + "\n    ],\n    \"status_code\": 202,\n    \"nextPath\": \"there's not page next\",\n    \"prevPath\": \"there's not page prev\" \n}";
}
