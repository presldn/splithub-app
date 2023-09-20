package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.exception.Problem;
import com.splithub.splithubapp.mongodb.GroupRepository;
import com.splithub.splithubapp.mongodb.document.GroupDocument;
import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.request.JoinGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import com.splithub.splithubapp.rest.response.JoinGroupResponse;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GroupDocumentServiceImplTest {

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

    GroupDocument mockGroupDocument = mock(GroupDocument.class);
    when(mockGroupDocument.id()).thenReturn("GroupID");
    when(mockGroupDocument.name()).thenReturn(GROUP_NAME);
    when(mockGroupDocument.getUserIds()).thenReturn(List.of(USER_ID));
    when(mockGroupDocument.groupCode()).thenReturn(GROUP_CODE);
    Instant createdAt = Instant.now();
    when(mockGroupDocument.createdAt()).thenReturn(createdAt);

    when(groupRepository.insert(any(GroupDocument.class))).thenReturn(mockGroupDocument);

    CreateGroupResponse response = groupService.createGroup(request);

    assertEquals(GROUP_ID, response.groupId());
    assertEquals(GROUP_NAME, response.groupName());
    assertEquals(List.of(USER_ID), response.userIds());
    assertEquals(GROUP_CODE, response.groupCode());
    assertEquals(createdAt, response.createdAt());
  }

  @Test
  void testJoinGroup_Successful() {
    GroupDocument groupDocument = new GroupDocument();
    groupDocument.setId("1");
    groupDocument.setGroupCode("validCode");
    groupDocument.setName("groupName");


    List<String> userIds = new java.util.ArrayList<>();
    userIds.add("anotherUserId");
    groupDocument.setUserIds(userIds);

    when(groupRepository.findByGroupCode("validCode")).thenReturn(Optional.of(groupDocument));

    Either<Problem, JoinGroupResponse> result = groupService.joinGroup(new JoinGroupRequest("validCode", "userId"));

    assertEquals(Either.right(new JoinGroupResponse("1", "groupName", "validCode")), result);
    assertTrue(groupDocument.getUserIds().contains("userId"));
  }

  @Test
  void testJoinGroup_UserAlreadyInGroup() {
    GroupDocument groupDocument = new GroupDocument();
    groupDocument.setUserIds(List.of("userId"));

    when(groupRepository.findByGroupCode("validCode")).thenReturn(Optional.of(groupDocument));

    Either<Problem, JoinGroupResponse> result = groupService.joinGroup(new JoinGroupRequest("validCode", "userId"));

    assertEquals(Either.left(new Problem("Already connected to group.")), result);
  }

  @Test
  void testJoinGroup_InvalidGroupCode() {
    when(groupRepository.findByGroupCode("invalidCode")).thenReturn(Optional.empty());

    Either<Problem, JoinGroupResponse> result = groupService.joinGroup(new JoinGroupRequest("invalidCode", "userId"));

    assertEquals(Either.left(new Problem("Enter a valid group code.")), result);
  }


}
