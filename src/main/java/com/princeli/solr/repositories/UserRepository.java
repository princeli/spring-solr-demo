package com.princeli.solr.repositories;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.princeli.solr.model.DocumentSolr;
import com.princeli.solr.model.UserSolr;

public interface UserRepository extends SolrCrudRepository<UserSolr, String> {
		
}
