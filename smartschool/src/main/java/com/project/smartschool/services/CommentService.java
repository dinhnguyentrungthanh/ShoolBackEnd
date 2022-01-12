package com.project.smartschool.services;

import com.project.smartschool.dto.request.CommentRequest;
import com.project.smartschool.dto.response.CommentReponse;
import com.project.smartschool.entities.CommentEntity;

public interface CommentService extends IBaseService<CommentEntity, String>,
										IDtoService<CommentReponse, String, CommentRequest>,
										IPageService<CommentReponse> {
	
}
