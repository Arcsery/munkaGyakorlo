package com.app.gyakorlo.controller;
import com.app.gyakorlo.jwt.AuthTokenFilter;
import com.app.gyakorlo.jwt.JwtUtils;
import com.app.gyakorlo.model.User;
import com.app.gyakorlo.payload.response.MessageResponse;
import com.app.gyakorlo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Optional<User> getUserById(@PathVariable Integer id){
        return userRepository.findById(id);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @PathVariable Integer id, User user){

        String token = jwtUtils.getJwtFromCookies(request);
        String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);

        if(tokenUsername.equals(user.getUsername())){
            userRepository.updateUserById(user.getId(),user.getName(),user.getEmail(),user.getAge());
            return ResponseEntity.ok(new MessageResponse("User modified sucessfully"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("It's not your profile!"));
        }
    }
    

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
    }
}
