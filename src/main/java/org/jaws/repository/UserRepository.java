package org.jaws.repository;

import org.jaws.model.UserDO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JawsRepository<UserDO, Long> {
    boolean existsByUserNameOrEmail(String userName, String email);

    Optional<UserDO> findByUserName(String username);
}
