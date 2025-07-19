package com.frg.autocare.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstants {
    //Endpoints
    public static final String API_BASE_PATH = "/api";
    public static final String DEFAULT_API_VERSION = "/v1";

    public static final String BASE_ENDPOINT_CARS = API_BASE_PATH + DEFAULT_API_VERSION + "/cars";
    public static final String BASE_ENDPOINT_AUTH = API_BASE_PATH + DEFAULT_API_VERSION + "/auth";

    //Sorting and pagination
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";
}
