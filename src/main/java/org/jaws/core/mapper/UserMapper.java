package org.jaws.core.mapper;

import org.jaws.core.dto.UserDTO;
import org.jaws.model.UserDO;

import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DataMapper<UserDO, UserDTO> {

  public UserDO toDO(UserDTO userDTO, Object... args) {
    // TODO map DTO to DO
    return null;
  }

  public UserDTO toDTO(UserDO userDO, Object... args) {
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(userDO.getId());
    userDTO.setFirstName(userDO.getFirstName());
    userDTO.setLastName(userDO.getLastName());
    userDTO.setEmail(userDO.getEmail());
    userDTO.setUserName(userDO.getUserName());
    return userDTO;
  }

}
