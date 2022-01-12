package com.project.smartschool.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smartschool.dto.response.ChapterResponse;
import com.project.smartschool.dto.response.KnowledgeResponse;
import com.project.smartschool.dto.response.ReviewQuestionResponse;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.ClassEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.errors.NotFoundException;
import com.project.smartschool.repository.ClassRepository;
import com.project.smartschool.repository.RoleGroupRepository;
import com.project.smartschool.repository.RoleRepository;
import com.project.smartschool.repository.UserRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.ChapterService;
import com.project.smartschool.services.ClientQueryService;
import com.project.smartschool.services.KnowledgeService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.services.ReviewQuestionService;

@Service
public class ClientQueryServiceImpl implements ClientQueryService {

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private BlockService blockService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private MathDesignService mathDesignService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private ReviewQuestionService reviewQuestionService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private RoleGroupRepository roleGroupRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Object fetchAllChaptersFromBlockAndMajorAndMathdesign(String blockSeo, String majorSeo,
			String mathdesignSeo) {

		BlockEntity block = blockService.findByUrl(blockSeo);
		MajorEntity major = majorService.findByUrl(majorSeo);
		MathDesignEntity md = mathDesignService.findByUrl(mathdesignSeo);

		if (ObjectUtils.anyNull(block, major, md)) {
			throw new NotFoundException("Invalid url");
		}

		boolean matchMajorInBlock = block.getMajors().contains(major.getId());
		boolean matchMathDesignInMajor = major.getMathDesigns().contains(md.getId());

		if (!matchMajorInBlock || !matchMathDesignInMajor) {
			throw new NotFoundException("Invalid url");
		}

		List<ChapterEntity> chapters = chapterService.findAllByIds(md.getChapters()).stream().filter(c -> c.isEnable())
				.collect(Collectors.toList());

		return chapters.stream().map(c -> {
			List<KnowledgeEntity> knows = knowledgeService.findAllByIds(c.getKnowledges()).stream()
					.filter(k -> k.isEnable()).collect(Collectors.toList());
			return ChapterResponse.builder().build().convertFromEntity(c, null, knows);
		});
	}

	@Override
	public Object fetchAllChaptersFromBlockAndMajorAndMathdesignAndChapterAndKnowledge(String blockSeo, String majorSeo,
			String mathdesignSeo, String chapterSeo, String knowledgeSeo) {

		BlockEntity block = blockService.findByUrl(blockSeo);
		MajorEntity major = majorService.findByUrl(majorSeo);
		MathDesignEntity md = mathDesignService.findByUrl(mathdesignSeo);
		ChapterEntity c = chapterService.findByUrl(chapterSeo);
		KnowledgeEntity kn = knowledgeService.findByUrl(knowledgeSeo);

		if (ObjectUtils.anyNull(block, major, md, c, kn)) {
			throw new NotFoundException("Invalid url");
		}

		boolean matchMajorInBlock = block.getMajors().contains(major.getId());
		boolean matchMathDesignInMajor = major.getMathDesigns().contains(md.getId());
		boolean matchChapterinMathDesign = md.getChapters().contains(c.getId());
		boolean mathKnowledgeInChapter = c.getKnowledges().contains(kn.getId());

		if (!matchMajorInBlock || !matchMathDesignInMajor || !matchChapterinMathDesign || !mathKnowledgeInChapter) {
			throw new NotFoundException("Invalid url");
		}

		Set<ReviewQuestionResponse> reviewQuestion = reviewQuestionService
				.findAllByIds(kn.getReviewQuestions().stream().collect(Collectors.toList())).stream()
				.filter(rv -> rv.isEnable())
				.map(rv -> ReviewQuestionResponse.builder().build().convertFromEntity(rv)).collect(Collectors.toSet());

		return KnowledgeResponse.builder().build().convertFromEntity(kn, c, reviewQuestion);

	}

	@Override
	public Object findByUsernameUser(String usernameSeo) {
		UserEntity user = userRepository.findByUsername(usernameSeo);
		return mappingClassToResponse(user);
	}
	
	private UserResponse mappingClassToResponse(UserEntity user) {
		Set<String> classes = StreamSupport.stream(classRepository.findAllById(user.getClasses()).spliterator(), false)
				.map(ClassEntity::getId).collect(Collectors.toSet());

		// Binding data và convert to UserResponse
		return UserResponse.builder().build().convertFromEntity(user, null, null, classes);
	}

	@Override
	public Object findByIdUsername(String idSeo) {
		UserEntity user = userRepository.findByIdAndEnableTrue(idSeo);
		return mappingClassToResponse(user);
	}
}
