package com.hamuksoft.emilytalks.apps.backend;

import com.hamuksoft.emilytalks.modules.shared.security.JwtUtil;
import com.hamuksoft.emilytalks.modules.user.application.LoginUser;
import com.hamuksoft.emilytalks.modules.user.application.RegisterUser;
import com.hamuksoft.emilytalks.modules.user.application.SearchUser;
import com.hamuksoft.emilytalks.modules.user.application.dto.UserDTO;
import com.hamuksoft.emilytalks.modules.user.domain.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final SearchUser searchUser;
    private final JwtUtil jwtUtil;

    public UserController(RegisterUser registerUser, LoginUser loginUser, SearchUser searchUser, JwtUtil jwtUtil) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.searchUser = searchUser;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        registerUser.execute(user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles());
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletResponse response) {
        try {
            loginUser.execute(user.getUsername(), user.getPassword());
            String token = jwtUtil.generateToken(user.getUsername());
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = searchUser.findByUsername(username);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO(user.get());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<UserDTO> verifyToken(@CookieValue(value = "token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Claims claims = jwtUtil.getClaimsFromToken(token);
            String username = claims.getSubject();
            Optional<User> userFound = searchUser.findByUsername(username);

            if (userFound.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            User user = userFound.get();
            UserDTO userDTO = new UserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}