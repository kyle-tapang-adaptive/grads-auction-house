package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.controller.dto.CreateUserRequest;
import com.weareadaptive.auction.controller.dto.UserResponse;
import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  UserResponse createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
    User newUser = userService.create(
            createUserRequest.username(),
            createUserRequest.password(),
            createUserRequest.firstName(),
            createUserRequest.lastName(),
            createUserRequest.organisation());
    return new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), newUser.getOrganisation());
  }
}
