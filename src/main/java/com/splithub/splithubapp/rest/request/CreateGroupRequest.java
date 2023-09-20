package com.splithub.splithubapp.rest.request;

import java.util.List;

public record CreateGroupRequest(String userId, String name, String currency, List<String> participantNames) {}
