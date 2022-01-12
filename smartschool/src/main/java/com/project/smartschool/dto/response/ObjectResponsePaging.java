package com.project.smartschool.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectResponsePaging<T> {
	
	private long totalElements;
	private long totalPages;
	private long size;
	private long currentPage;
	private List<T> elements;
}
