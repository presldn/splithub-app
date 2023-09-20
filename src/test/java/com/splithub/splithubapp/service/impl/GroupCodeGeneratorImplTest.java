package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.mongodb.GroupRepository;
import com.splithub.splithubapp.mongodb.document.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GroupCodeGeneratorImplTest {

  @Mock private GroupRepository groupRepository;

  @InjectMocks private GroupCodeGeneratorImpl groupCodeGenerator;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGenerateUniqueCode() {

    when(groupRepository.findByGroupCode("ABC123")).thenReturn(Optional.empty());
    when(groupRepository.findByGroupCode("XYZ789")).thenReturn(Optional.empty());

    String code = groupCodeGenerator.generate();

    assertNotNull(code);
  }

}
