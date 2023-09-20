package com.splithub.splithubapp.service.impl;

import com.splithub.splithubapp.exception.Problem;
import com.splithub.splithubapp.mongodb.GroupRepository;
import com.splithub.splithubapp.mongodb.document.GroupDocument;
import com.splithub.splithubapp.mongodb.document.Participant;
import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.request.JoinGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import com.splithub.splithubapp.rest.response.JoinGroupResponse;
import com.splithub.splithubapp.service.GroupService;
import java.util.List;

import io.vavr.control.Either;
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

    GroupDocument groupDocument =
        groupRepository.insert(
            new GroupDocument(
                groupRequest.name(),
                groupRequest.currency(),
                groupCode,
                groupRequest.userId(),
                participants));

    return new CreateGroupResponse(
        groupDocument.id(),
        groupDocument.name(),
        groupDocument.getUserIds(),
        groupDocument.groupCode(),
        groupDocument.createdAt());
  }

  @Override
  public Either<Problem, JoinGroupResponse> joinGroup(JoinGroupRequest request) {

    return groupRepository
        .findByGroupCode(request.groupCode())
        .map(
            groupDocument -> {
              if (groupDocument.getUserIds().contains(request.userId())) {
                return Either.<Problem, JoinGroupResponse>left(
                    new Problem("Already connected to group."));
              } else {
                return joinGroupOrError(request.userId(), groupDocument);
              }
            })
        .orElse(Either.left(new Problem("Enter a valid group code.")));
  }

  private Either<Problem, JoinGroupResponse> joinGroupOrError(
      String userId, GroupDocument groupDocument) {

    groupDocument.getUserIds().add(userId);

    try {
      groupRepository.save(groupDocument);
      return Either.right(
          new JoinGroupResponse(
              groupDocument.id(), groupDocument.name(), groupDocument.groupCode()));
    } catch (Exception e) {
      return Either.left(new Problem("Failed to join group: " + e.getMessage()));
    }
  }
}
