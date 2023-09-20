package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.mongodb.GroupRepository;
import com.splithub.splithubapp.mongodb.document.Group;
import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupServiceImplTest {

  public static final String GROUP_ID = "GroupID";
  public static final String GROUP_NAME = "MyGroup";
  public static final String USER_ID = "user1";
  public static final String GROUP_CODE = "GRCODE";
  @Mock private GroupRepository groupRepository;
  @Mock private GroupCodeGenerator groupCodeGenerator;
  @InjectMocks private GroupServiceImpl groupService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateGroup() {

    CreateGroupRequest request =
        new CreateGroupRequest(USER_ID, GROUP_NAME, "USD", Arrays.asList("Alice", "Bob"));
    when(groupCodeGenerator.generate()).thenReturn(GROUP_CODE);

    Group mockGroup = mock(Group.class);
    when(mockGroup.id()).thenReturn("GroupID");
    when(mockGroup.name()).thenReturn(GROUP_NAME);
    when(mockGroup.connectedUserIds()).thenReturn(List.of(USER_ID));
    when(mockGroup.groupCode()).thenReturn(GROUP_CODE);
    Instant createdAt = Instant.now();
    when(mockGroup.createdAt()).thenReturn(createdAt);

    when(groupRepository.insert(any(Group.class))).thenReturn(mockGroup);

    CreateGroupResponse response = groupService.createGroup(request);

    assertEquals(GROUP_ID, response.groupId());
    assertEquals(GROUP_NAME, response.groupName());
    assertEquals(List.of(USER_ID), response.userIds());
    assertEquals(GROUP_CODE, response.groupCode());
    assertEquals(createdAt, response.createdAt());
  }
}
