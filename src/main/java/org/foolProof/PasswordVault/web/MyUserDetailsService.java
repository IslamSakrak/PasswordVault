package org.foolProof.PasswordVault.web;

import jakarta.transaction.Transactional;
import org.foolProof.PasswordVault.User.Client;
import org.foolProof.PasswordVault.User.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @Transactional public class MyUserDetailsService implements UserDetailsService {

    @Autowired private ClientRepository clientRepository;

    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Client client = clientRepository.findClientByEmail(email);
        if ( client == null ) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }

        return User.withUsername(client.getEmail()).password(client.getPassword()).authorities("USER").build();
    }

}
