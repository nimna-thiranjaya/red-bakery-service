package com.redbakery.redbakeryservice.common;

public class ApplicationRoute {
    public static final String Root = "/api/v1";

    public class User {
        public static final String Root = ApplicationRoute.Root + "/user";
        public static final String Save = "/register";
        public static final String GetProfile = "/profile";
        public static final String UpdateProfile = "/profile";
        public static final String DeleteProfile = "/profile";
    }

    public class Authentication {
        public static final  String Root = ApplicationRoute.Root + "/auth";
        public static final String Login = "/login";
        public static final String RefreshToken = "/refresh-token";
    }

    public class FoodCategory {
        public static final String Root = ApplicationRoute.Root + "/food-category";
        public static final String GetAll = "/";
        public static final String GetById = "/{id}";
        public static final String Save = "/";
        public static final String Update = "/{id}";
        public static final String Delete = "/{id}";
    }
}
