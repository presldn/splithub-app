package com.splithub.splithubapp.rest.response;

import java.time.Instant;
import java.util.List;

public record CreateGroupResponse(
    String groupId,
    String groupName,
    List<String> userIds,
    String groupCode,
    Instant createdAt) {}
