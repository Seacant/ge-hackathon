package com.hackathon.nku.repositories;

import com.hackathon.nku.models.Email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer> {

  Email findFirstByAssignedTo(Integer id);
}