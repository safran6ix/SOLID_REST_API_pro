package com.example.rest.resource;

import com.example.rest.model.User;
import com.example.rest.repository.UserRepositoryImpl;
import com.example.rest.service.UserService;
import com.example.rest.service.UserServiceImpl;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    // Dependency Inversion Principle (DIP) - Injects dependency
    public UserResource() {
        // In a real app, this would be injected by a framework
        this.userService = new UserServiceImpl(new UserRepositoryImpl());
    }

    @GET
    public Response getAllUsers() {
        return Response.ok(userService.getAllUsers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            User user = userService.getUserById(id);
            return Response.ok(user).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createUser(User user) {
        try {
            User created = userService.createUser(user);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, User user) {
        try {
            User updated = userService.updateUser(id, user);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return Response.ok()
                        .entity("User deleted successfully")
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found with id: " + id)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}