package org.jaws.controller;

import lombok.RequiredArgsConstructor;
import org.jaws.core.dto.UserDTO;
import org.jaws.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(userId,userDTO));
    }

}
