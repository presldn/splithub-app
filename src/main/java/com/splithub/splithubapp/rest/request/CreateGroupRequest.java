package com.splithub.splithubapp.rest.request;

import java.util.List;

// TODO: 20/09/2023 add validation for request classes
public record CreateGroupRequest(String userId, String name, String currency, List<String> participantNames) {}
