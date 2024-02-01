package br.com.isllascurty.todolist.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

/*
 * String - texto
 * Integer - numeros inteiros
 * Double - Numeros com casas decimais 0.0000
 * Float - Numeros com casas decimais 0.000
 * char - Letra
 * Date - Data
 * void - não ter retorno do metodo
 */
@PostMapping("/")
public ResponseEntity create(@RequestBody UserModel userModel){
   // System.out.println(userModel.getUsername());
   var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null){
            System.out.println("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário Já existe.");
        }

    var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

   var userCreated = this.userRepository.save(userModel);
   return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
}

}
