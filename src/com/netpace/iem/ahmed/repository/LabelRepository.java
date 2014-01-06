package com.netpace.iem.ahmed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netpace.iem.ahmed.model.Label;

public interface LabelRepository extends BaseRepository<Label, Integer> {
	@Query("SELECT l FROM Label l WHERE l.name = :name")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Label> findByName(@Param("name") String name) ;
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Label l WHERE l.id = :id")
	public int deleteById(@Param("id") Integer id) ;
}
