package br.com.autenticador.autenticador.models;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBUSU")
public class UserModel implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    @Column(name = "USUEMAIL")
    private String email;
    @Column(name = "USUSENHA")
    private String password;
    @Column(name = "USUNAME")
    private String name;
    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    @Override
    public String getPassword() {
       return this.password;
    }
    @Override
    public String getUsername() {
        return this.name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;

    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;

    }
    @Override
    public boolean isEnabled() {
        return true;

    }



}
