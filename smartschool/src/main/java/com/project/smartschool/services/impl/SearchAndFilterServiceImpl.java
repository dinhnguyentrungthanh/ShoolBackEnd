package com.project.smartschool.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.smartschool.dto.request.KnowledgeFilterRequest;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.ChapterService;
import com.project.smartschool.services.KnowledgeService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.services.SearchAndFilterService;

@Service
public class SearchAndFilterServiceImpl implements SearchAndFilterService {

	@Autowired
	private BlockService blockService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private MathDesignService mathDesignService;

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Override
	public List<MathDesignEntity> fetchAllMathDesignFromBlocks(List<String> blockIds) {

		List<BlockEntity> blocks = blockService.findAllByIds(blockIds);

		Set<String> majorIds = new HashSet<String>();
		blocks.stream().forEach(block -> majorIds.addAll(block.getMajors()));

		List<MajorEntity> majors = majorService.findAllByIds(majorIds).stream().filter(m -> m.isEnable()).collect(Collectors.toList());

		Set<String> mdIds = new HashSet<String>();
		majors.stream().forEach(major -> mdIds.addAll(major.getMathDesigns()));

		return mathDesignService.findAllByIds(mdIds).stream().filter(m -> m.isEnable()).collect(Collectors.toList());
	}

	@Override
	public List<ChapterEntity> fetchAllChapterFromMathDesign(List<String> mdIds) {
		List<MathDesignEntity> mds = mathDesignService.findAllByIds(mdIds).stream().filter(m -> m.isEnable()).collect(Collectors.toList());
		Set<String> chapterIds = new HashSet<String>();
		mds.stream().forEach(md -> chapterIds.addAll(md.getChapters()));

		return chapterService.findAllByIds(chapterIds).stream().filter(m -> m.isEnable()).collect(Collectors.toList());
	}

	@Override
	public List<KnowledgeEntity> searchKnowLedge(KnowledgeFilterRequest filter) {

		boolean onlyChooseChapter = !CollectionUtils.isEmpty(filter.getChapters());
		if (onlyChooseChapter) {
			return knowledgeService.searchingAll(filter.getKeywords()).stream()
					.filter(k -> filter.getChapters().contains(k.getChapter())).collect(Collectors.toList());
		}

		boolean onlyChooseMathDesign = !CollectionUtils.isEmpty(filter.getMathDesigns()) && !onlyChooseChapter;
		if (onlyChooseMathDesign) {
			List<String> mdIds = filter.getMathDesigns().stream().collect(Collectors.toList());
			return foundChapterToSearch(mdIds, filter.getKeywords());
		}
		
		boolean onlyChooseBlock = !CollectionUtils.isEmpty(filter.getBlocks()) && !onlyChooseMathDesign && !onlyChooseChapter;
		if (onlyChooseBlock) {
			List<MathDesignEntity> mathDesigns = this.fetchAllMathDesignFromBlocks(filter.getBlocks().stream().collect(Collectors.toList()))
					.stream().filter(m -> m.isEnable()).collect(Collectors.toList());
			List<String> mdIds = mathDesigns.stream().map(m -> m.getId()).collect(Collectors.toList());
			return foundChapterToSearch(mdIds, filter.getKeywords());
		}
		return knowledgeService.searchingAll(filter.getKeywords()).stream().filter(m -> m.isEnable()).collect(Collectors.toList());
	}
	
	private List<KnowledgeEntity> foundChapterToSearch(List<String> mdIds, String keywords) {
		List<ChapterEntity> chapters = this
				.fetchAllChapterFromMathDesign(mdIds).stream().filter(m -> m.isEnable()).collect(Collectors.toList());
		
		Set<String> chapterIds = chapters.stream().map(c -> c.getId()).collect(Collectors.toSet());
		return knowledgeService.searchingAll(keywords).stream()
				.filter(k -> chapterIds.contains(k.getChapter()))
				.filter(m -> m.isEnable())
				.collect(Collectors.toList());
	}

}
