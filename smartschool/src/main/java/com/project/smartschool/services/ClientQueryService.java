package com.project.smartschool.services;

public interface ClientQueryService {
	
	Object fetchAllChaptersFromBlockAndMajorAndMathdesign(String blockSeo, String majorSeo, String mathdesignSeo);
	
	Object fetchAllChaptersFromBlockAndMajorAndMathdesignAndChapterAndKnowledge(String blockSeo, String majorSeo, String mathdesignSeo, String chapterSeo, String knowledSeo);
	
	Object findByUsernameUser(String usernameSeo);
	
	Object findByIdUsername(String idSeo);
}
