package org.jaws.service;

import lombok.RequiredArgsConstructor;
import org.jaws.dto.UserDTO;
import org.jaws.mapper.UserMapper;
import org.jaws.model.UserDO;
import org.jaws.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;


  public UserDTO findById(Long id) {
    // TODO Create custom exception
    return userRepository.findById(id)
        .map(userMapper::toDto)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public Page<UserDTO> findAllUsers(Pageable pageable) {
    Page<UserDO> users = userRepository.findAll(pageable);
    return users.map(userMapper::toDto);
  }

}
