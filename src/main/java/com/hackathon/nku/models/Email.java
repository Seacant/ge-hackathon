package com.hackathon.nku.models;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name="email")
@Table(name="email")
public class Email {
  private @Id @GeneratedValue @Column(name="emailId") Integer emailId;
  private String subject;
  private String body;
  private String sender;
  private String recipient;

  // The app never touches the date itself other than displaying and
  // JDBC-Derived dates are a convoluted mess so sendDate is a string
  private String sendDate;
  private String attachment;

  private String assignedTo;
  private String assignedWhen;

  private Boolean isSpam;

  Email(){}
}