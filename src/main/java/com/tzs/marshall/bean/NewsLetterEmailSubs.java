package com.tzs.marshall.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsLetterEmailSubs {

	private Long subsId;
	private String email;
	private String alternateEmail;

	public NewsLetterEmailSubs (Long subsId, String email) {
		this.subsId = subsId;
		this.email = email;
	}
}
