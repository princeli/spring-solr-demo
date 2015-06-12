package com.princeli.solr.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.princeli.solr.model.DocumentSolr;

public interface DocumentRepository extends SolrCrudRepository<DocumentSolr, String> {
	  @Query("id:?0 OR title:?1")
	  public List<DocumentSolr> findByQueryAnnotation(String id,String title, Sort sort);
	  
	  @Query("title:?0")
	  public DocumentSolr findByTitle(String title);

}
