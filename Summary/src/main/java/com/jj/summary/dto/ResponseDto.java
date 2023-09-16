package com.jj.summary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
//	@JsonProperty("resultCode")
	private Integer resultCode;
//	@JsonProperty("resultMsg")
	private String resultMsg;
//	@JsonProperty()
	private T data;
}
