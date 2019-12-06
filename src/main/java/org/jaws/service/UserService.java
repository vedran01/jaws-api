package org.jaws.service;

import lombok.RequiredArgsConstructor;
import org.jaws.core.dto.UserDTO;
import org.jaws.core.exception.BadRequestException;
import org.jaws.core.exception.ResourceNotFoundException;
import org.jaws.core.mapper.DataMapper;
import org.jaws.model.UserDO;
import org.jaws.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DataMapper<UserDO, UserDTO> userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found found"));
    }

    public Page<UserDTO> findAllUsers(Pageable pageable) {
        Page<UserDO> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    public UserDTO saveUser(UserDTO user) {

        if (userRepository.existsByUserNameOrEmail(user.getUserName(), user.getEmail())) {
            throw new BadRequestException("Duplicate user name or email");
        }
        UserDO userDO = userMapper.toDO(user);
        userDO.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(userDO));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        UserDO user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return userMapper.toDTO(userRepository.save(user));
    }
}
