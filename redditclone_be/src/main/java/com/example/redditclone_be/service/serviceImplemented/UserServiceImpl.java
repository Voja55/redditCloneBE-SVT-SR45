package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.model.dto.UserDTO;
import com.example.redditclone_be.model.entity.ERole;
import com.example.redditclone_be.model.entity.User;
import com.example.redditclone_be.repository.UserRepository;
import com.example.redditclone_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if (user.isPresent()){
            return null;
        }
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        //newUser.setAvatar(userDTO.getAvatar());
        newUser.setDescription(userDTO.getDescription());
        newUser.setDisplayName(userDTO.getDisplayName());
        newUser.setRole(ERole.USER);
        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public List<User> getAllUsers()  {
        List<User> usersList = userRepository.findAll();
        return usersList;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }


}
