package com.redbakery.redbakeryservice.common;

public class ApplicationRoute {
    public static final String Root = "/api/v1";

    public class User {
        public static final String Root = ApplicationRoute.Root + "/user";
        public static final String Save = "/register";
        public static final String GetProfile = "/profile";
    }

    public class Authentication {
        public static final  String Root = ApplicationRoute.Root + "/auth";
        public static final String Login = "/login";
        public static final String RefreshToken = "/refresh-token";
    }
}
