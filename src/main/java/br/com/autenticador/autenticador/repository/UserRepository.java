package br.com.autenticador.autenticador.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.autenticador.autenticador.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer>{
    UserModel findByEmail(String email);
    List<UserModel> findAll();
}
