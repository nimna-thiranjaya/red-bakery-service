package com.redbakery.redbakeryservice.common;

public class ApplicationRoute {
    public static final String Root = "/api/v1";

    public class User {
        public static final String Root = ApplicationRoute.Root + "/user";
        public static final String Save = "/register";
        public static final String GetProfile = "/profile";
        public static final String UpdateProfile = "/profile";
        public static final String DeleteProfile = "/profile";
        public static final String GetAll = "/";
    }

    public class Authentication {
        public static final String Root = ApplicationRoute.Root + "/auth";
        public static final String Login = "/login";
        public static final String RefreshToken = "/refresh-token";
    }

    public class FoodCategory {
        public static final String Root = ApplicationRoute.Root + "/food-category";
        public static final String GetAll = "/";
        public static final String GetById = "/{id}";
        public static final String Save = "/";
        public static final String Update = "/{id}";
        public static final String ActiveInactive = "/{id}/active-inactive";
        public static final String Delete = "/{id}";
    }

    public class FoodType {
        public static final String Root = ApplicationRoute.Root + "/food-type";
        public static final String GetAll = "/";
        public static final String GetById = "/{id}";
        public static final String Save = "/";
        public static final String Update = "/{id}";
        public static final String ActiveInactive = "/{id}/active-inactive";
        public static final String Delete = "/{id}";
        public static final String GetByCategory = "/food-category/{id}";
    }

    public class Product {
        public static final String Root = ApplicationRoute.Root + "/product";
        public static final String GetAll = "/";
        public static final String GetById = "/{id}";
        public static final String Save = "/";
        public static final String Update = "/{id}";
        public static final String ActiveInactive = "/{id}/active-inactive";
        public static final String Delete = "/{id}";
        public static final String GetByFoodType = "/food-type/{id}";
        public static final String SearchProduct = "/search";
    }

    public class Cart {
        public static final String Root = ApplicationRoute.Root + "/cart";
        public static final String AddToCart = "/add-to-cart";
        public static final String GetCart = "/get-cart";
        public static final String UpdateCart = "/update-cart";
    }

    public class WishList {
        public static final String Root = ApplicationRoute.Root + "/wishlist";
        public static final String AddToWishList = "/product/{id}";
        public static final String GetWishList = "/";
        public static final String RemoveFromWishList = "/remove";


    }
}

