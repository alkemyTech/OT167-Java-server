package com.alkemy.ong.seeder;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override

    public void run(String... args) throws Exception {
    loadUser();
    }

    private void loadUser() {
        if(userRepository.count() == 0){
            loadUserEntity();
        }
    }

    private void loadUserEntity() {

        Collection<Role> admins = new ArrayList();
        if (roleRepository.count() == 0) {
            Role role = new Role(
                    "ROLE_ADMIN",
                    "Has the privileges from both roles");
            roleRepository.save(role);
            admins.add(role);
        }

       Collection<Role> users = new ArrayList();
        if (roleRepository.count() == 1) {
            Role role = new Role(
                    "ROLE_USER",
                    "Privileges limited modifying and viewing your data");
            roleRepository.save(role);
            users.add(role);
        }

        createUser("Victor","Salvatierra","victor@mail.com","12345678","/img", admins);
        createUser("Facundo","Villalba","facundo@mail.com","12345678","/img", admins);
        createUser("Andres","Rodirguez","andres@mail.com","12345678","/img", admins);
        createUser("Pablo","Ibañez","pablo@mail.com","12345678","/img", admins);
        createUser("Mickaela","Tarazaga","mickaela@mail.com","12345678","/img", admins);
        createUser("Agustin","Garcia","agustin@mail.com","12345678","/img", admins);
        createUser("Luz","Brito","luz@mail.com","12345678","/img", admins);
        createUser("Luciano","Lattante","luciano@mail.com","12345678","/img", admins);
        createUser("Gabriel","Rosa","gabriel@mail.com","12345678","/img", admins);
        createUser("Horacio","Cruz","horacio@mail.com","12345678","/img", admins);


        createUser("Diego","Padilla","diego@mail.com","12345678","/img", users);
        createUser("Carlos","Rodriguez","carlos@mail.com","12345678","/img", users);
        createUser("Juan","Morata","juan@mail.com","12345678","/img", users);
        createUser("Valeria","Villalba","valeria@mail.com","12345678","/img", users);
        createUser("Nicole","Lopez","nicole@mail.com","12345678","/img", users);
        createUser("Norma","Gomez","norma@mail.com","12345678","/img", users);
        createUser("Raquel","Diaz","raquel@mail.com","12345678","/img", users);
        createUser("Romina","Perez","romina@mail.com","12345678","/img", users);
        createUser("Horacio","Martin","horacio@mail.com","12345678","/img", users);
        createUser("Abel","Ojeda","abel@mail.com","12345678","/img", users);

    }

    private void createUser(String firstName, String lastName, String email, String password,String photo, Collection<Role> roles) {

        UserEntity user = new UserEntity(
                firstName,
                lastName,
                email,
                passwordEncoder.encode(password),
                photo,
                roles
        );
        userRepository.save(user);
    }
}
