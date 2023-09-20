package com.splithub.splithubapp.mongodb;

import com.splithub.splithubapp.mongodb.document.GroupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<GroupDocument, String> {

    Optional<GroupDocument> findByGroupCode(String groupCode);

}
