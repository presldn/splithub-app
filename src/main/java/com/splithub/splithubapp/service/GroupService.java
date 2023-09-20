package com.splithub.splithubapp.service;

import com.splithub.splithubapp.rest.request.CreateGroupRequest;
import com.splithub.splithubapp.rest.response.CreateGroupResponse;

public interface GroupService {
    CreateGroupResponse createGroup(CreateGroupRequest groupInput);
    
}
