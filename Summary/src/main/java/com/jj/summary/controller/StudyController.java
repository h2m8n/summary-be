//package com.jj.summary.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.jj.summary.domain.Study;
//import com.jj.summary.dto.CreateStudyRequestDto;
//import com.jj.summary.service.StudyService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/study")
//@RequiredArgsConstructor
//public class StudyController {
//	private final StudyService studyService;
//	
//	
//	@PostMapping("/create")
//	public ResponseEntity<String> createStudy(CreateStudyRequestDto createStudyRequestDto) {
//		System.out.println("test");
//		studyService.create(
//				Study.builder()
//				.name(createStudyRequestDto.getName())
//				.maxUser(createStudyRequestDto.getMaxUser())
//				.fileName(createStudyRequestDto.getFileName())
//				.subject(createStudyRequestDto.getName())
//				.book(createStudyRequestDto.getBook())
//				.bookAuthor(createStudyRequestDto.getBookAuthor())
//				.bookCompany(createStudyRequestDto.getBookCompany())
//				.startDate(createStudyRequestDto.getStartDate())
//				.endDate(createStudyRequestDto.getEndDate())
//				.onwerUserId(createStudyRequestDto.getOwnerUserId())
//				.build());
//		return ResponseEntity.ok("123");
//	 }
//	 @PostMapping("/getInfo")
//	 public ResponseEntity<Study> getStudyInfo(int studyId) {
//		 
//		 return ResponseEntity.ok(studyService.getInfo(studyId));
//	 }
//	 
//}
