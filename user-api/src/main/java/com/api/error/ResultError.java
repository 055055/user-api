package com.api.error;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@Setter
@Builder
public class ResultError {

	private String code;
	private String message;
	private HttpStatus httpStatus;
	private List<FieldValue> fieldValues;

	@Getter
	@Builder
	public static class FieldValue {
		private String field;
		private Object value;
		private String reason;
	}
}