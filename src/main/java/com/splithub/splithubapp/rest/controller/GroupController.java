package com.splithub.splithubapp.rest.controller;

import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import com.splithub.splithubapp.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @PostMapping("/createGroup")
  public ResponseEntity<Object> createGroup(@RequestBody CreateGroupRequest request) {
    CreateGroupResponse createdGroup = groupService.createGroup(request);
    if (createdGroup != null) {
      return ResponseEntity.ok(createdGroup);
    }
    return ResponseEntity.badRequest().body("Could not create group");
  }

}
