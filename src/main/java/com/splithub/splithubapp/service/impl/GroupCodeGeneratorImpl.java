package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.mongodb.GroupRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GroupCodeGeneratorImpl implements GroupCodeGenerator {

  private final GroupRepository groupRepository;

  public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public GroupCodeGeneratorImpl(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Override
  public String generate() {

    String code = generateCode();

    while (groupRepository.findByGroupCode(code).isPresent()) {
      // TODO: 19/09/2023 edgecase where groupCodes could run out?
      code = generateCode();
    }

    return code;
  }

  private String generateCode() {
    StringBuilder sb = new StringBuilder();
    SecureRandom random = new SecureRandom();

    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }

    return sb.toString();
  }
}
