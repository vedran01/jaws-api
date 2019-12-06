package org.jaws.repository;

import org.jaws.model.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JawsRepository<UserDO, Long> {
    boolean existsByUserNameOrEmail(String userName, String email);

}
