package com.project.smartschool.util;

import com.github.slugify.Slugify;

public class SeoUtils {
	
	public static String toFriendlyUrl(String content) {
		Slugify slg = new Slugify().withLowerCase(true);
		String seoUrl = slg.slugify(content.replaceAll("đ", "d").replaceAll("Đ", "D"));
		return seoUrl;
	}
	
	public static String toFriendlyUrlGeneric(String content) {
		return toFriendlyUrl(content) + "-" + StringUtils.generatorId();
	}
}
