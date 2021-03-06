package com.hackathon.nku.controllers;

import com.hackathon.nku.repositories.EmailRepository;
import javax.transaction.Transactional;
import com.hackathon.nku.models.Email;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
class EmailController {
  private final EmailRepository repository;

  EmailController(EmailRepository repository) {
    this.repository = repository;
  }

  /**
   * PUT /email/1
   * {
   *   is_spam: null
   * }
   * 
  */
  @PutMapping("/email/{id}")
  Email updateEmail(@RequestBody Email newEmail, @PathVariable Integer id) {
    Email email = repository.findById(id).get();

    email.setIsSpam(newEmail.getIsSpam());
    repository.save(email);

    return email;
  }

  @GetMapping("/email/{id}")
  Email Email(@RequestBody Email deletedEmail, @PathVariable Integer id) {
    Email email = repository.findById(id).get();
    return email;
  }

  @PutMapping("/email/next")
  Email get_next_set(){
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();

    synchronized(this) {
      Email email = repository.findFirstByAssignedTo(null);
      email.setAssignedTo(auth.getName());
      repository.save(email);
      return email;
    }
  }
}