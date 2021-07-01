package com.sbs.exam.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Like {
	private String relTypeCode;
	private int relId;
	private int memberId;
	private boolean isLike;
	private boolean isDislike;
	
}
