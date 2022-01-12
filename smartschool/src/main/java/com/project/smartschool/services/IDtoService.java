package com.project.smartschool.services;

import java.util.List;

public interface IDtoService<OUT, ID, IN> {
	
	/**
	 * Triết xuất danh sách Entity từ DB với đầu ra đã được chuyển đồi là DTO output
	 * @return Danh sách DTO output của Entity
	 */
	List<OUT> fetchAll();
	
	/**
	 * Triết xuất Entity từ DB với đầu ra đã được chuyển đồi là DTO output
	 * @param id Khóa ngoại của Entity
	 * @return DTO output của Entity
	 */
	OUT fetchById(ID id);
	
	/**
	 * Tạo Entity dựa vào DTO input
	 * @param inputDto 
	 * @return DTO output của Entity
	 */
	OUT saveUsingDTO(IN inputDto);
	
	/**
	 * Cập nhật Entity dựa vào DTO input
	 * @param inputDto
	 * @return DTO output của Entity
	 */
	OUT updateUsingDTO(IN inputDto);
	
}
