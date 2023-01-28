package com.example.spj.repository;

import com.example.spj.entity.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<UserAuthority,Long> {

}
