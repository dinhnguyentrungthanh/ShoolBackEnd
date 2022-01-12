package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.project.smartschool.dto.request.BlockRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ClassEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.BlockRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.ClassService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.util.SeoUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BlockServiceImpl implements BlockService {

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private BlockService blockService;

	@Autowired
	private MajorService majorService;

	@Autowired
	ClassService classService;

	@Override
	public List<BlockEntity> findAll() {
		return blockRepository.findAll();
	}

	@Override
	public BlockEntity findById(String id) {
		return blockRepository.findById(id).orElse(null);
	}

	@Override
	public BlockEntity save(BlockEntity entity) {
		return blockRepository.save(entity);
	}

	@Override
	public List<BlockEntity> saveAll(List<BlockEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockEntity update(BlockEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		BlockEntity entity = verify(id);
		entity.setEnable(false);

		List<ClassEntity> classes = classService.findAllByIds(entity.getClasses()).stream().map(e -> {
			e.setEnable(false);
			return e;
		}).collect(Collectors.toList());

		classService.saveAll(classes);

		List<MajorEntity> majors = majorService.findAllByIds(entity.getMajors()).stream().map(e -> {
			e.setEnable(false);
			return e;
		}).collect(Collectors.toList());

		majorService.saveAll(majors);

		return blockRepository.save(entity) != null;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for (String id : ids) {
			try {
				this.deleteById(id);
				m.put(id, true);
			} catch (Exception e) {
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public boolean isExistedById(String id) {
		return blockRepository.existsById(id);
	}

	@Override
	public List<BlockResponse> fetchAll() {
		List<BlockEntity> blocks = blockRepository.findAllByEnableTrue();
		return blocks.stream().map(c -> mappingBlockToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public BlockResponse fetchById(String id) {
		BlockEntity blockEnitity = blockRepository.findById(id).orElse(null);

		if (ObjectUtils.anyNull(blockEnitity))
			throw new ValidateException("Không tìm thấy Khối");

		return mappingBlockToResponse(blockEnitity);
	}

	@Override
	public BlockResponse saveUsingDTO(BlockRequest inputDto) {
		List<BlockEntity> blocks = blockRepository.findAllByEnableFalse();
		for (BlockEntity blockEntity : blocks) {
			if (blockService.isExistedByBlockname(inputDto.getBlockname().trim()) && blockEntity.getBlockname().equals(inputDto.getBlockname()))
				throw new ValidateException("Tên khối này đã tồn tại");
		}
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public BlockResponse updateUsingDTO(BlockRequest inputDto) {
		BlockEntity bls = verify(inputDto.getId());

		BlockEntity blsAll = blockRepository.findByBlockname(inputDto.getBlockname());

		if (ObjectUtils.allNotNull(bls) && ObjectUtils.allNotNull(blsAll)) {
			if (!inputDto.getId().equals(blsAll.getId())
					&& inputDto.getBlockname().trim().equals(blsAll.getBlockname())) {
				throw new ValidateException("Tên Khối này đã tồn tại");
			}

			if (inputDto.getId().equals(blsAll.getId())
					&& !inputDto.getBlockname().trim().equals(blsAll.getBlockname())) {
				throw new ValidateException("Tên Khối này đã tồn tại");
			}
		}

		boolean hasAnyMajorNotExisted = inputDto.getMajors().stream().anyMatch(e -> !majorService.isExistedById(e));
		if (hasAnyMajorNotExisted)
			throw new ValidateException("Danh sách môn không hợp lệ");

		return saveOrUpdateUsingDTO(inputDto, blockService.findById(inputDto.getId()));
	}

	@Override
	public ObjectResponsePaging<BlockResponse> findAll(int page, int size) {
		Page<BlockEntity> pages = this.blockRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<BlockResponse> elements = pages.getContent().stream().map(c -> mappingBlockToResponse(c))
				.collect(Collectors.toList());

		return ObjectResponsePaging.<BlockResponse>builder().totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages()).size(size).currentPage(page).elements(elements).build();
	}

	@Override
	public ObjectResponsePaging<BlockResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private BlockResponse mappingBlockToResponse(BlockEntity blockEntity) {
		List<MajorEntity> major = majorService.findAllByIds(blockEntity.getMajors());
		List<ClassEntity> classes = classService.findAllByIds(blockEntity.getClasses());
		return BlockResponse.builder().build().convertFromEntity(blockEntity, major, classes);
	}

	@Override
	public boolean isExistedByBlockname(String blockname) {
		return !ObjectUtils.anyNull(blockRepository.findByBlockname(blockname));
	}

	private BlockResponse saveOrUpdateUsingDTO(BlockRequest inputDto, BlockEntity blockEntity) {

		BlockEntity temp = inputDto.convertToEntity(blockEntity);
		String url = SeoUtils.toFriendlyUrl(temp.getBlockname());
		if (inputDto.getId() == null) {
			if (blockRepository.existsByUrl(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getBlockname());
			}
		}else {
			BlockEntity tempUpdate = blockRepository.findByUrl(temp.getUrl());
			if(tempUpdate != null && !tempUpdate.getUrl().equals(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getBlockname());
			}
		}
		temp.setUrl(url);
		BlockEntity bls = blockService.save(temp);

		List<MajorEntity> majorsUpdated = inputDto.getMajors().stream().map(e -> mappingResquestToEntity(e, bls))
				.collect(Collectors.toList());
		List<ClassEntity> classesUpdated = inputDto.getClasses().stream().map(e -> mappingResquestToEntity2(e, bls))
				.collect(Collectors.toList());
		return BlockResponse.builder().build().convertFromEntity(bls, majorsUpdated, classesUpdated);
	}

	private MajorEntity mappingResquestToEntity(String majorid, BlockEntity bls) {
		MajorEntity m = majorService.findById(majorid);
		m.setBlock(bls.getId());
		return m;
	}

	private ClassEntity mappingResquestToEntity2(String classid, BlockEntity bls) {
		ClassEntity c = classService.findById(classid);
		c.setBlock(bls.getId());
		return c;
	}

	private BlockEntity verify(String id) {
		BlockEntity bls = blockRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(bls)) {
			throw new ValidateException("Lớp không tồn tại");
		}
		return bls;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BlockEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(blockRepository.findAllById(ids).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public boolean deleteMajors(String blockId, List<String> majorIds) {
		try {
			BlockEntity block = verify(blockId);

			// Get Major Ids from BLock
			Set<String> majorIdsDb = block.getMajors();

			// Delete Major Ids
			majorIdsDb.removeAll(majorIds);

			// Reset major ids and update
			block.setMajors(majorIdsDb);
			this.save(block);

			List<MajorEntity> me = this.majorService.findAllByIds(majorIds);
			// Get all majors from Block
			List<MajorEntity> majors = me.stream().map(m -> {
				m.setBlock(null);
				return m;
			}).collect(Collectors.toList());

			majorService.saveAll(majors);

			return true;
		} catch (Exception e) {
			log.error("Method deleteMajors - ERROR: " + e.getMessage());
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public boolean deleteClasses(String blockId, List<String> classIds) {
		try {
			BlockEntity block = verify(blockId);

			// Get Class Ids from BLock
			Set<String> classsDb = block.getClasses();

			// Delete Class Ids
			classsDb.removeAll(classIds);

			// Reset Class ids and update
			block.setClasses(classsDb);
			this.save(block);

			List<ClassEntity> me = this.classService.findAllByIds(classIds);

			// Get all Class from Block
			List<ClassEntity> classes = me.stream().map(m -> {
				m.setBlock(null);
				return m;
			}).collect(Collectors.toList());

			classService.saveAll(classes);

			return true;
		} catch (Exception e) {
			log.error("Method deleteClasses - ERROR: " + e.getMessage());
			throw new GlobalException(e.getMessage());
		}
	}

	@Override
	public BlockEntity findByUrl(String url) {
		BlockEntity block = blockRepository.findByUrl(url);
		if(ObjectUtils.anyNull(block) || !block.isEnable()) {
			throw new ValidateException("Not found Block Url");
		}
		return block;
	}

}
