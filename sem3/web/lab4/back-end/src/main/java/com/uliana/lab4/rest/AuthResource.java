package com.uliana.lab4.rest;

import com.uliana.lab4.dto.LoginRequest;
import com.uliana.lab4.dto.RegisterRequest;
import com.uliana.lab4.ejb.AuthBean;
import com.uliana.lab4.entity.UserEntity;
import com.uliana.lab4.dto.ProfileUpdateRequest;
import com.uliana.lab4.dto.MeDto;


import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @EJB
    private AuthBean authBean;

    @Context
    private HttpServletRequest request;

    @POST
    @Path("/login")
    public Response login(LoginRequest dto) {
        Optional<UserEntity> userOpt = authBean.login(dto.getLogin(), dto.getPassword());
        if (userOpt.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        UserEntity user = userOpt.get();

        HttpSession session = request.getSession(true);
        Long currentUserId = (Long) session.getAttribute("userId");

        // уже залогинен как другой юзер в этом же браузере
        if (currentUserId != null && !currentUserId.equals(user.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Вы уже вошли в другого пользователя в этом браузере. Сначала выйдите.\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // если уже залогинен этим же юзером, просто подтверждаем
        session.setAttribute("userId", user.getId());

        return Response.ok(toMeDto(user)).build();
    }


    @POST
    @Path("/register")
    public Response register(RegisterRequest dto) {
        try {
            UserEntity user = authBean.register(
                    dto.getLogin(), dto.getPassword(), dto.getFirstName(), dto.getLastName()
            );

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();

            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());

            return Response.status(Response.Status.CREATED).entity(toMeDto(user)).build();

        } catch (EJBException ejb) {
            Throwable c = ejb.getCause();
            if (c instanceof IllegalArgumentException) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse("USER_EXISTS", "Пользователь уже существует"))
                        .build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("SERVER_ERROR", "Ошибка сервера"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("USER_EXISTS", "Пользователь уже существует"))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return Response.ok().build();
    }

    @GET
    @Path("/me")
    public Response me() {
        UserEntity user = authBean.getCurrentUser(request);
        if (user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        return Response.ok(toMeDto(user)).build();
    }

    @PUT
    @Path("/profile")
    public Response updateProfile(ProfileUpdateRequest dto) {
        UserEntity user = authBean.getCurrentUser(request);
        if (user == null) return Response.status(Response.Status.UNAUTHORIZED).build();

        UserEntity updated = authBean.updateProfile(user.getId(), dto.getFirstName(), dto.getLastName());
        return Response.ok(toMeDto(updated)).build();
    }

    private MeDto toMeDto(UserEntity u) {
        MeDto dto = new MeDto();
        dto.setId(u.getId());
        dto.setLogin(u.getLogin());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        return dto;
    }

    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse() {}
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public void setCode(String code) { this.code = code; }
        public void setMessage(String message) { this.message = message; }
    }
}
