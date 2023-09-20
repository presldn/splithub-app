package com.splithub.splithubapp.mongodb.document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groups")
public class Group {

  @Id private String id;
  private String name;
  private String photoUrl;
  private String currency;
  private Instant createdAt;
  private String groupCode;
  private List<String> connectedUserIds;
  private BigDecimal expensesTotal;
  private List<Participant> participants;

  public Group(
      String name,
      String currency,
      String groupCode,
      String userId,
      List<Participant> participants) {
    this.id = UUID.randomUUID().toString();
    this.createdAt = Instant.now();
    this.name = name;
    this.currency = currency;
    this.groupCode = groupCode;
    this.expensesTotal = BigDecimal.ZERO;
    this.connectedUserIds = List.of(userId);
    this.participants = participants;
  }

  public String id() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String photoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String currency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Instant createdAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public String groupCode() {
    return groupCode;
  }

  public void setGroupCode(String groupCode) {
    this.groupCode = groupCode;
  }

  public List<String> connectedUserIds() {
    return connectedUserIds;
  }

  public void setConnectedUserIds(List<String> connectedUserIds) {
    this.connectedUserIds = connectedUserIds;
  }

  public BigDecimal expensesTotal() {
    return expensesTotal;
  }

  public void setExpensesTotal(BigDecimal expensesTotal) {
    this.expensesTotal = expensesTotal;
  }

  public List<Participant> participants() {
    return participants;
  }

  public void setParticipants(List<Participant> participants) {
    this.participants = participants;
  }
}
