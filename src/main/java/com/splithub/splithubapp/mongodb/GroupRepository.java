package com.splithub.splithubapp.mongodb;

import com.splithub.splithubapp.mongodb.document.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    Optional<Group> findByGroupCode(String groupCode);

}
