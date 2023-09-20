package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.mongodb.GroupRepository;
import com.splithub.splithubapp.mongodb.document.Group;
import com.splithub.splithubapp.mongodb.document.Participant;
import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import com.splithub.splithubapp.service.GroupService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final GroupCodeGenerator groupCodeGenerator;

  public GroupServiceImpl(GroupRepository groupRepository, GroupCodeGenerator groupCodeGenerator) {
    this.groupRepository = groupRepository;
    this.groupCodeGenerator = groupCodeGenerator;
  }

  @Override
  public CreateGroupResponse createGroup(CreateGroupRequest groupRequest) {

    String groupCode = groupCodeGenerator.generate();

    List<Participant> participants =
        groupRequest.participantNames().stream().map(Participant::new).toList();

    Group group =
        groupRepository.insert(
            new Group(
                groupRequest.name(),
                groupRequest.currency(),
                groupCode,
                groupRequest.userId(),
                participants));

    return new CreateGroupResponse(
        group.id(), group.name(), group.connectedUserIds(), group.groupCode(), group.createdAt());
  }
}
