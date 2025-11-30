package ku.cs.project_SE.service;



import com.nimbusds.jose.util.Pair;
import ku.cs.project_SE.dto.auth.RegisterRequestDto;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.exception.UserExistsException;
import ku.cs.project_SE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//Tee
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmailOrPhoneNumber(String identifier) {
        return userRepository.findByEmailOrPhoneNumber(identifier,identifier);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User createUser(RegisterRequestDto dto) throws RuntimeException {
        Optional<User> existingUserEmail = this.findByEmail(dto.email());
        Optional<User> existingUserPhoneNumber = this.findByPhoneNumber(dto.phoneNumber());

        if(existingUserEmail.isPresent() ){
            throw new UserExistsException("Email already exists");
        }
        else if (existingUserPhoneNumber.isPresent()){
            throw new UserExistsException("Phone number already exists");
        }

        User user = User.builder()
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.NORMAL)
                .build();

        userRepository.save(user);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
//        System.out.println("XXX "+identifier);
        if (identifier.contains("@")){

        }
        User user = userRepository.findByEmailOrPhoneNumber(identifier,identifier)
                .orElseThrow(() -> new UsernameNotFoundException(identifier + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}
