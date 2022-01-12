package com.project.smartschool.constraints;

public interface ApiConstraint {
	
	public static final String API_BASE = "/api/";
	
	public static final String API_ENPOINT_USER = "user";	
	public static final String API_ENPOINT_USER_ID = "user/{id}";
	public static final String API_ENPOINT_USER_COUNT = "user/{id}/count";
	
	public static final String API_ENPOINT_ROLE = "role";
	
	public static final String API_ENPOINT_ROLE_GROUP = "rolegroup";

	public static final String API_ENPOINT_COMMENT = "comment";
	
	public static final String API_ENPOINT_CLASS = "class";
	public static final String API_ENPOINT_CLASS_ID = "class/{id}";
	public static final String API_ENPOINT_CLASS_ID_USER = "class/{classId}/user";
	public static final String API_ENPOINT_CLASS_ID_USER_ID = "class/{classId}/user/{userId}";
	
	public static final String API_ENPOINT_THEORY = "theory";
	
	public static final String API_ENPOINT_REVIEW_QUESTION = "reviewquestion";
	public static final String API_ENPOINT_REVIEW_QUESTION_ID = "reviewquestion/{id}";
	
	public static final String API_ENPOINT_KNOWLEDGE = "knowledge";
	public static final String API_ENPOINT_KNOWLEDGE_ID = "knowledge/{id}";
	public static final String API_ENPOINT_KNOWLEDGE_BY_CHAPTER = "knowledge/{id}/chapter";
	
	public static final String API_ENPOINT_BLOCK = "block";	
	public static final String API_ENPOINT_BLOCK_ID = "block/{id}";	
	public static final String API_ENPOINT_BLOCK_BY_MAJOR = "block/{id}/major";	
	public static final String API_ENPOINT_BLOCK_BY_CLASS = "block/{id}/class";
	public static final String API_ENPOINT_BLOCK_BY_URL = "block/byurl/{url}";
	
	public static final String API_ENPOINT_MAJOR = "major";
	
	public static final String API_ENPOINT_MATHDESIGN = "mathdesign";
	
	public static final String API_ENPOINT_CHAPTER = "chapter";
	
	public static final String API_ROLEGROUP_CHAPTER = "rolegroup";
	
	public static final String API_ENPOINT_TEST_TYPE = "test-type";
	
	public static final String API_ENPOINT_TEST_TYPE_ID = "test-type/{id}";
	
	public static final String API_ENPOINT_TEST = "test";
	
	public static final String API_ENPOINT_TEST_ID = "test/{id}";
	
	public static final String API_ENPOINT_POINT = "point";
	public static final String API_ENPOINT_POINT_ID = "point/{id}";
	public static final String API_ENPOINT_POINT_ID_STREAM = "point/{id}/stream";	
	public static final String API_ENPOINT_POINT_DETAIL = "point-detail";

	public static final String API_ENPOINT_PAGE_PARAM = "/page/{page}/size/{size}";
	
	public static final String API_ENDPOINT_SEARCH_MATHDESIGN_FROM_BLOCK = "search/block/mathdesign";	
	public static final String API_ENDPOINT_SEARCH_CHAPTER_FROM_MATHDESIGN = "search/mathdesign/chapter";	
	public static final String API_ENDPOINT_SEARCH_KNOWLEDGE_BY_FILTER = "search/chapter";
	
	public static final String API_ENPOINT_AUTH_LOGIN = "auth/login";	
	public static final String API_ENPOINT_AUTH_REFRESH = "auth/refreshtoken";
	public static final String API_ENPOINT_AUTH_REGISTER = "auth/register";	
	public static final String API_ENPOINT_AUTH_CLASS = "auth/class";
	
	public static final String API_ENPOINT_CHAPTER_F_BLOCK_F_MAJOR_F_MATHDESIGN = "block/{blockSeo}/major/{majorSeo}/mathdesign/{mathdesignSeo}/chapter";
	public static final String API_ENPOINT_CHAPTER_F_BLOCK_F_MAJOR_F_MATHDESIGN_F_CHAPTER_F_KNOWLEDGE = "block/{blockSeo}/major/{majorSeo}/mathdesign/{mathdesignSeo}/chapter/{chapterSeo}/knowledge/{knowledgeSeo}";
	public static final String API_ENPOINT_USERNAME = "{usernameSeo}";
	public static final String API_ENPOINT_ID = "{idSeo}";
	
	public static final String API_ENPOINT_CHANGE_PASSWORD = "changepws";
	
	public static final String API_ENPOINT_CHANGE_PASSWORD_ADMIN = "changepws/admin";

	public static final String API_ENPOINT_UPLOAD_MEDIA_AVATAR = "media/{id}/avatar";
	
	public static final String API_ENPOINT_UPLOAD_MEDIA_FILE = "media/file";
	
}
