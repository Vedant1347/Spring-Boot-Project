package com.demoSpring;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalJPA extends JpaRepository<Hospital, Integer>{

	@Query("select h from Hospital h where h.name=?1 and h.location=?2")
//	@Query(value="Select * from hospital where name=?1 and location=?2",nativeQuery = true)
	public List<Hospital> findByNameLocation(String name, String location);
	public List<Hospital> findAll();
	public Optional<Hospital> findById(int id);

}