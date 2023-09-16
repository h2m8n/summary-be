package com.jj.summary.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRequestDto {
	private String name;
	
	private Integer maxUser;
	
	private String fileName;
	
	private String subject;
	
	private String book;
	
	private String bookAuthor;
	
	private String bookCompany;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private Integer ownerUserId;
}
