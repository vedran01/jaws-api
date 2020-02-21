package org.jaws.security;

import org.jaws.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JawsUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    public JawsUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUserName(username)
                .map(JawsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + "not found"));
    }
}
