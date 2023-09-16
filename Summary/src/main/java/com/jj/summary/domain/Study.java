package com.jj.summary.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Study {
	
	@Id
    @GeneratedValue
    private Integer id;
	
	private String name;
	
	private Integer maxUser;
	
	private String fileName;
	
	private String subject;
	
	private String book;
	
	private String bookAuthor;
	
	private String bookCompany;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer onwerUserId;
}
