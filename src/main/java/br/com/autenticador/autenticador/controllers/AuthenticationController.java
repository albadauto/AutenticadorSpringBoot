package br.com.autenticador.autenticador.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autenticador.autenticador.dto.UserDTO;
import br.com.autenticador.autenticador.models.ResponseModel;
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
    public ResponseEntity<?> CreateUser(@RequestBody UserModel user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        if(userRepository.findByEmail(user.getEmail()) != null){

            return ResponseEntity.badRequest().body(new ResponseModel<>(){{
                success = false;
                message = "Erro: email já existe na base";
                data = null;
            }});
        }
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseModel<>(){{
            success = true;
            message = "Usuário criado com sucesso";
            data = null;
        }});
    }


    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody UserDTO dto){
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var authentication = this.authorizationManager.authenticate(usernamePasswordAuthenticationToken);
            var user = (UserModel) authentication.getPrincipal();
            var token = tokenService.GenerateToken(user);
            return ResponseEntity.ok(new ResponseModel<String>() {{
                success = true;
                message = "Token gerado com sucesso";
                data = token;
            }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
