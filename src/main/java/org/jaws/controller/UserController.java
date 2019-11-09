package org.jaws.controller;

import lombok.RequiredArgsConstructor;
import org.jaws.dto.UserDTO;
import org.jaws.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  Page<UserDTO> findAll(Pageable pageable) {
    return userService.findAllUsers(pageable);
  }

  @GetMapping("/{userId}")
  UserDTO findById(@PathVariable Long userId) {
    return userService.findById(userId);
  }

}
