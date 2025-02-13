package com.youcode.cuisenio.features.auth.mapper;

import com.youcode.cuisenio.features.auth.dto.request.LoginRequest;
import com.youcode.cuisenio.features.auth.dto.response.RegisterResponse;
import com.youcode.cuisenio.features.auth.dto.response.LoginResponse;
import com.youcode.cuisenio.features.auth.dto.request.RegisterRequest;
import com.youcode.cuisenio.features.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "password", ignore = true)
    RegisterResponse userToRegisterResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
//    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", defaultExpression = "java(Role.getDefault())")
    @Mapping(target = "recipes", ignore = true)
    @Mapping(target = "mealPlans", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    User registerRequestToUser(RegisterRequest registerRequest);

    // Convertir LoginRequest en User (optionnel, car LoginRequest ne contient que l'e-mail et le mot de passe)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
//    @Mapping(target = "lastName", ignore = true)
//    @Mapping(target = "role", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
//    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    @Mapping(target = "mealPlans", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    User loginRequestToUser(LoginRequest loginRequest);

    @Mapping(target = "token", source = "token")
    LoginResponse loginResponseFromToken(String token);
}