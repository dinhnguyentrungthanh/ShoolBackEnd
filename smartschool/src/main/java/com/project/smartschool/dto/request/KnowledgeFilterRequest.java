package com.project.smartschool.dto.request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KnowledgeFilterRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keywords;
	private Set<String> blocks = new HashSet<String>();
	private Set<String> mathDesigns = new HashSet<String>();
	private Set<String> chapters = new HashSet<String>();

}
