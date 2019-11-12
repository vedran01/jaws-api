package org.jaws.service;

import lombok.RequiredArgsConstructor;
import org.jaws.core.dto.UserDTO;
import org.jaws.core.exception.ResourceNotFoundException;
import org.jaws.core.mapper.DataMapper;
import org.jaws.core.mapper.UserMapper;
import org.jaws.model.UserDO;
import org.jaws.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final DataMapper<UserDO, UserDTO> userMapper;


  public UserDTO findById(Long id) {
    return userRepository.findById(id)
        .map(userMapper::toDTO)
        .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found found"));
  }

  public Page<UserDTO> findAllUsers(Pageable pageable) {
    Page<UserDO> users = userRepository.findAll(pageable);
    return users.map(userMapper::toDTO);
  }

}
