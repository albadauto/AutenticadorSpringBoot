package br.com.autenticador.autenticador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.autenticador.autenticador.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }
    
}
