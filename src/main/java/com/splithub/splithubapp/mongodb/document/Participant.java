package com.splithub.splithubapp.mongodb.document;

import java.math.BigDecimal;
import java.util.UUID;

public class Participant {
  private String participantId;
  private String name;
  private BigDecimal balance;
  private String photoUrl;

  public Participant(String name) {
    this.participantId = UUID.randomUUID().toString();
    this.name = name;
    this.balance = BigDecimal.ZERO;
  }

  public String participantId() {
    return participantId;
  }

  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal balance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String photoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
}
