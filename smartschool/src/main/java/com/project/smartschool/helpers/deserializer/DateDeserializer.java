package com.project.smartschool.helpers.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String ISO_DATE = ("yyyy-MM-dd'T'HH:mm'Z'");
	private SimpleDateFormat formatter;

	public DateDeserializer() {
		super();
		this.formatter = new SimpleDateFormat(ISO_DATE);
	}

	public DateDeserializer(String dateFormat) {
		super();
		if (StringUtils.isNotBlank(dateFormat))
			this.formatter = new SimpleDateFormat(dateFormat);
		else
			this.formatter = new SimpleDateFormat(ISO_DATE);
	}

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String timestamp = jp.getText().trim();
		try {
			return formatter.parse(timestamp);
		} catch (ParseException e) {
			logger.warn(e.getMessage());
			return new Date();
		}
	}
}
