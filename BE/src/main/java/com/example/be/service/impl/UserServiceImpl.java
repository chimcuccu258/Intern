package com.example.be.service.impl;

import com.example.be.model.Role;
import com.example.be.model.UserRole;
import com.example.be.notfound.NotFoundException;
import com.example.be.repository.RoleRepository;
import com.example.be.repository.UserRepository;
import com.example.be.service.UserService;
import com.example.be.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private  final  RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }




    @Override
    public ResponseEntity<String> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
@Override
    public ResponseEntity<String> registerUser(User user) {
            // Kiểm tra xem người dùng đã tồn tại hay chưa
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }
        if (!iValidPhone(user.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Invalid phone format");
        }


        // Gán vai trò cho người dùng (ở đây mặc định là User)
        Optional<Role> userRoleOptional = roleRepository.findByRoleName("User");
        Role userRole = userRoleOptional.orElseThrow(() -> new NotFoundException("Role not found"));
        UserRole userUserRole = new UserRole();
        userUserRole.setRole(userRole);
        Role idrole = new Role();
        idrole.setId(3L);
        userUserRole.setUser(user);
        user.getUserRole().add(userUserRole);
        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);
    return ResponseEntity.ok("User registered successfully");

}

    private boolean iValidPhone(String phoneNumber){
        String regex = "(84|0[3|5|7|8|9])+([0-9]{8})";
        return phoneNumber.matches(regex);
    }
    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-].+$";
        return email.matches(regex);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
