package com.aeye.aeaimb.mkpb.service.impl.kg;

import cn.hutool.core.bean.BeanUtil;
import com.aeye.aeaimb.mkpb.mapper.kg.KgKnowledgeFieldMapper;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeFieldService;
import com.aeye.cdss.admin.api.entity.KgKnowledgeField;
import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.aeye.aeaimb.mkpb.service.kg.KgAttributesService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识字段配置表
 *
 * @author cdss
 * @date 2023-12-06 17:13:49
 */
@Service
public class KgKnowledgeFieldServiceImpl extends ServiceImpl<KgKnowledgeFieldMapper, KgKnowledgeField> implements KgKnowledgeFieldService {

	@Override
	public List<KgKnowledgeFieldVO> getAListOfItems(String type, boolean lookId) {
		LambdaQueryWrapper<KgKnowledgeField> lqw = new LambdaQueryWrapper<>();
		lqw.eq(KgKnowledgeField::getTypeCode, type);
		//只查询要展示的
		lqw.eq(KgKnowledgeField::getFieldCanPreview, 0);
		lqw.gt(lookId, KgKnowledgeField::getIdx, 1);    // 是否不查看ID
		lqw.orderByAsc(KgKnowledgeField::getIdx);
		lqw.orderByAsc(KgKnowledgeField::getFieldCode);

		List<KgKnowledgeField> list = this.list(lqw);
		List<KgKnowledgeFieldVO> ret = BeanUtil.copyToList(list, KgKnowledgeFieldVO.class);
		//注释的代码不知道有啥用
//		int is = 0;
//		for (int i = 0; i < ret.size(); i++) {
//			if (ret.get(i).getFieldDataType().equals("rich")) {
//				is = i;
//				break;
//			}
//		}
//		// 获取最后两个元素
//		KgKnowledgeFieldVO lastElement = ret.get(list.size() - 1);
//		KgKnowledgeFieldVO secondLastElement = ret.get(list.size() - 2);
//
//		// 指定的索引位置
//
//		// 在指定索引处插入元素
//		ret.add(is, secondLastElement);
//		ret.add(is + 1, lastElement);
//
//		// 删除最后两个元素（因为它们已经被复制到新位置）
//		ret.remove(ret.size() - 1);
//		ret.remove(ret.size() - 1);
		return ret;
	}
}