package com.example.spj.repository;

import com.example.spj.entity.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
