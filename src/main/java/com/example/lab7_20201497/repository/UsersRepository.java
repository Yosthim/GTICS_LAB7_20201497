package com.example.lab7_20201497.repository;

import com.example.lab7_20201497.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    public List<Users> findByAuthorizedResource(int resourceId);
}
