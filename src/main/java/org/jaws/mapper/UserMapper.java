package org.jaws.mapper;

import org.jaws.dto.UserDTO;
import org.jaws.model.UserDO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDO toDO(UserDTO userDTO){
    // TODO map DTO to DO
    return null;
  }

  public UserDTO toDto(UserDO userDO){
    UserDTO userDTO = new UserDTO();
    userDTO.setUserId(userDO.getId());
    userDTO.setFirstName(userDO.getFirstName());
    userDTO.setLastName(userDO.getLastName());
    userDTO.setEmail(userDO.getEmail());
    userDTO.setUserName(userDO.getUserName());
    return userDTO;
  }

}
