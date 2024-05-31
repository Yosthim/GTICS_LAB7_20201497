package com.example.lab7_20201497.controller;

import com.example.lab7_20201497.entity.Users;
import com.example.lab7_20201497.repository.ResourcesRepository;
import com.example.lab7_20201497.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ws")
public class WebServiceController {
    final UsersRepository usersRepository;
    final ResourcesRepository resourcesRepository;
    public WebServiceController(UsersRepository usersRepository, ResourcesRepository resourcesRepository) {
        this.usersRepository = usersRepository;
        this.resourcesRepository = resourcesRepository;
    }

    //Gestión de excepciones
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> handleException(HttpServletRequest request) {
        HashMap<String, String> response = new HashMap<>();
        if (request.getMethod().equals("POST")) {
            response.put("status", "error");
            response.put("message", "Invalid request, you must send a user");
        }
        return ResponseEntity.badRequest().body(response);
    }

    //Crear un usuario
    @PostMapping(value = "/createUser")
    public ResponseEntity<HashMap<String, Object>> createUser(@RequestBody Users user,
                                                              @RequestParam(value = "fetchId", required = false) boolean fetchId) {
        HashMap<String, Object> response = new HashMap<>();
        usersRepository.save(user);
        if (fetchId) {
            response.put("id", user.getUserId());
        }
        response.put("status", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Listado de usuarios autorizados para acceder a un recurso
    @GetMapping(value = "/authorizedList")
    public List<Users> getAuthorizedList(@RequestParam(value = "resourceId") String idStr) {
        try {
            int resourceId = Integer.parseInt(idStr);
            return usersRepository.findByAuthorizedResource(resourceId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
