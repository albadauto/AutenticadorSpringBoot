package br.com.autenticador.autenticador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autenticador.autenticador.dto.UserDTO;
import br.com.autenticador.autenticador.models.UserModel;
import br.com.autenticador.autenticador.repository.UserRepository;
import br.com.autenticador.autenticador.services.TokenService;

@RestController
@RequestMapping("/Auth")
public class AuthenticationController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authorizationManager;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/")
    public String HelloWorld(){
        return "Ola mundo";
    }

    @PostMapping("/CreateUser")
    public UserModel CreateUser(@RequestBody UserModel user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/Login")
    public String Login(@RequestBody UserDTO dto){
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var authentication = this.authorizationManager.authenticate(usernamePasswordAuthenticationToken);
            var user = (UserModel) authentication.getPrincipal();

            return tokenService.GenerateToken(user);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
