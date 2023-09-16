package com.jj.summary.service;

import org.springframework.stereotype.Service;

import com.jj.summary.domain.Study;
import com.jj.summary.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyService {

	private final StudyRepository studyRepositroy;
	
	public String create(Study study) {
		System.out.println(study);
		 try {
			 studyRepositroy.save(study);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		
		return "";
	}
	
	public Study getInfo(int studyId) {
		return studyRepositroy.findById(studyId);
	}
}
