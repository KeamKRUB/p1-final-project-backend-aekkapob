package ku.cs.project_SE.controller;

import ku.cs.project_SE.dto.user.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRequestDto dummy(UUID id) {
        UserRequestDto d = new UserRequestDto();
        d.setId(id);
        d.setFirst("DUMMY_FNAME");
        d.setLastname("DUMMY_LNAME");
        d.setEmail("DUMMY_EMAIL");
        d.setPassword("DUMMY_PASSWORD");
        return d;
    }

    @GetMapping("/token/view")
    public ResponseEntity<UserRequestDto> getUserByToken(@RequestBody String token) {
        if (token == null || token.trim().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            UUID uuid = UUID.randomUUID();
            UserRequestDto u = dummy(uuid);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/view")
    public ResponseEntity<UserRequestDto> getUserById(@RequestBody String id) {
        if (id == null || id.trim().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            UUID uuid = UUID.fromString(id);
            UserRequestDto u = dummy(uuid);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody UserRequestDto userDTO) {
        if(userDTO.getId()==null) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return  new ResponseEntity<>("Edit Sucuessfully", HttpStatus.OK);
    }
}
