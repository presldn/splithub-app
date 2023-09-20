package com.splithub.splithubapp.service;

import com.splithub.splithubapp.exception.Problem;
import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.request.JoinGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;
import com.splithub.splithubapp.rest.response.JoinGroupResponse;
import io.vavr.control.Either;

public interface GroupService {
    CreateGroupResponse createGroup(CreateGroupRequest groupInput);

    Either<Problem, JoinGroupResponse> joinGroup(JoinGroupRequest request);
}
