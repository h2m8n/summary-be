package com.jj.summary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jj.summary.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Integer>{
	Study findById(int studyId);
}
