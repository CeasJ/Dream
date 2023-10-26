package com.backend.dream.repository;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.dto.AuthorityDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {


}
