package com.aeye.aeaimb.mkpb.service.impl.kg;

import com.aeye.aeaimb.mkpb.mapper.kg.KgTagMapper;
import com.aeye.aeaimb.mkpb.service.kg.KgTagService;
import com.aeye.cdss.admin.api.dto.TagNode;
import com.aeye.cdss.admin.api.entity.KgTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author lideng
 * @date 2023-12-27 10:24:43
 */
@Service
public class KgTagServiceImpl extends ServiceImpl<KgTagMapper, KgTag> implements KgTagService {
	@Autowired
	private KgTagMapper kgTagMapper;

	@Override
	public List<TagNode> getTreeStructure(String type) {
		QueryWrapper<KgTag> qw = new QueryWrapper<>();
		qw.lambda().like(KgTag::getTagType,type);
		List<KgTag> tags = kgTagMapper.selectList(qw);
		return convertToTree(tags);
	}



	@Override
	public KgTag getTagById(String id) {
		KgTag kgTag = kgTagMapper.selectById(id);
		return kgTag;
	}

	@Override
	public List<String> findAllParentIds(String initialParentId) {
		List<String> parentIds = new ArrayList<>();
		String currentParentId = initialParentId;

		while (currentParentId != null && !currentParentId.isEmpty()) {
			// 假设 getEntityByParentId 是一个方法，根据 parentId 返回相应的实体
			KgTag entity = this.getById(currentParentId);
			if (entity != null) {
				parentIds.add(entity.getId());
				currentParentId = entity.getParentId();
			} else {
				break;
			}
		}

		return parentIds;
	}

	public void someServiceMethod(String parentId) {
		List<String> allParentIds = findAllParentIds(parentId);
		// 在这里使用 allParentIds
	}



	private List<TagNode> convertToTree(List<KgTag> tags) {
		Map<String, TagNode> dtoMap = tags.stream()
				.collect(Collectors.toMap(KgTag::getId, tag -> {
					TagNode dto = new TagNode();
					BeanUtils.copyProperties(tag, dto);
					return dto;
				}));

		dtoMap.values().forEach(dto -> {
			if (dto.getParentId() != null) {
				TagNode parent = dtoMap.get(dto.getParentId());
				if (parent != null){
					if (parent.getChildren() == null) {
						parent.setChildren(new ArrayList<>());
					}
					parent.getChildren().add(dto);
				}
			}
		});

		return dtoMap.values().stream()
				.filter(dto -> StringUtils.isEmpty(dto.getParentId()))
				.collect(Collectors.toList());
	}

}
