package com.aeye.aeaimb.mkpb.controller.kg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.security.component.PermissionService;
import com.aeye.aeaimb.mkpb.entity.sys.SysDictItem;
import com.aeye.aeaimb.mkpb.entity.sys.SysMenu;
import com.aeye.aeaimb.mkpb.service.sys.SysDictItemService;
import com.aeye.aeaimb.mkpb.service.sys.SysMenuService;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeFieldService;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeService;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeTypeService;
import com.aeye.aeaimb.mkpb.service.kg.KgTagService;
import com.aeye.cdss.admin.api.dto.GeneralPagingOfKnowledgeBase;
import com.aeye.cdss.admin.api.dto.KnowledgeCount;
import com.aeye.cdss.admin.api.dto.KnowledgeDiseaseDTO;
import com.aeye.cdss.admin.api.dto.KnowledgePreviewDTO;
import com.aeye.cdss.admin.api.entity.KgKnowledgeType;
import com.aeye.cdss.admin.api.entity.KgTag;
import com.aeye.cdss.admin.api.enums.ClassificationOfKnowledgeBaseEnum;
import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.aeye.cdss.admin.api.vo.KnowledgeListInParameter;
import com.aeye.cdss.admin.api.vo.KnowledgeTypeAsoc;
import com.aeye.cdss.admin.api.vo.KnowledgeUpdateRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 知识
 *
 * @author lideng
 * @date 2023-12-06 17:14:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kgKnowledge")
@Tag(description = "kgKnowledge", name = "知识管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgKnowledgeController {

	private final KgKnowledgeService kgKnowledgeService;

	private final KgKnowledgeTypeService kgKnowledgeTypeService;

	private final KgKnowledgeFieldService kgKnowledgeFieldService;

	private final PermissionService pms;

	private final SysMenuService sysMenuService;

	private final SysDictItemService sysDictItemService;

//	private final BaseDictKgMappingService baseDictKgMappingService;


	@Resource
	private KgTagService kgTagService;

	@Value("${image.path: http://192.168.16.132:8070}")
	private String publicPicturePath;

	/**
	 * 知识维护首页
	 *
	 * @return
	 */
	@Operation(summary = "知识维护首页", description = "知识维护首页")
	@GetMapping("/index")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<List<KnowledgeTypeAsoc>> getKgKnowledgePage() {
		List<KnowledgeCount> baseag = kgKnowledgeService.knowledgePaging();
		Map<String, Integer> base = baseag.stream()
				.collect(Collectors.toMap(KnowledgeCount::getName, KnowledgeCount::getCount));


		List<KnowledgeTypeAsoc> list = new ArrayList<>();
		List<KgKnowledgeType> listtype = kgKnowledgeTypeService.list();
		Collections.sort(listtype, new Comparator<KgKnowledgeType>() {
			@Override
			public int compare(KgKnowledgeType s1, KgKnowledgeType s2) {
				int num1 = Integer.parseInt(s1.getTypeCode().substring(1));
				int num2 = Integer.parseInt(s2.getTypeCode().substring(1));
				return Integer.compare(num1, num2);
			}
		});
		for (KgKnowledgeType kt : listtype) {
			KnowledgeTypeAsoc kta = new KnowledgeTypeAsoc();
			kta.setTypeCode(kt.getTypeCode());
			kta.setTypeName(kt.getTypeName());
			boolean bs = pms.hasPermission(kt.getTypeCode());
			kta.setJump(bs);
			if (bs) {
				SysMenu sm = sysMenuService.permissionAcquisition(kt.getTypeCode());
				kta.setUrl(sm.getPath());
			}

			kta.setNum(base.containsKey(kt.getTypeCode()) ? base.get(kt.getTypeCode()) : 0);
			list.add(kta);
		}
		return R.ok(list);
	}


	/**
	 * 临床指南分页
	 *
	 * @param page        分页对象
	 * @param kgKnowledge 知识
	 * @return
	 */
	@Operation(summary = "临床指南分页", description = "分页查询")
	@GetMapping("/getClinicalGuidelinePage")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<IPage<KnowledgeDiseaseDTO>> getClinicalGuidelinePage(@ParameterObject Page page, @ParameterObject KnowledgeListInParameter kgKnowledge) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		List<GeneralPagingOfKnowledgeBase> lookup = new ArrayList<>();
		lookup.add(new GeneralPagingOfKnowledgeBase("rangeOfApplication", "T2-4-0040"));
		lookup.add(new GeneralPagingOfKnowledgeBase("issueYear", "T2-3-0039"));

		List<GeneralPagingOfKnowledgeBase> where = new ArrayList<>();
		if (StringUtils.isNotBlank(kgKnowledge.getRangeOfApplication())) {
			where.add(new GeneralPagingOfKnowledgeBase("rangeOfApplication", "T2-5-0051", kgKnowledge.getRangeOfApplication()));
		}
		return R.ok(kgKnowledgeService.getUniversalPaging(page, ClassificationOfKnowledgeBaseEnum.CLINICAL_PATHWAY.getType(), kgKnowledge.getName(), lookup, where));
	}


	/**
	 * 通用分页
	 *
	 * @param page        分页对象
	 * @param kgKnowledge 知识
	 * @return
	 */
	@Operation(summary = "通用分页", description = "分页查询")
	@GetMapping("/universalPaging")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<IPage<KnowledgeDiseaseDTO>> universalPaging(@ParameterObject Page page, @ParameterObject KnowledgeListInParameter kgKnowledge) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		Map<String, List<GeneralPagingOfKnowledgeBase>> map = kgKnowledgeService.setKeys(kgKnowledge);

		IPage<KnowledgeDiseaseDTO> universalPaging = kgKnowledgeService.getUniversalPaging(page, kgKnowledge.getType(), kgKnowledge.getName(), map.get("lookup"), map.get("where"));

		List<KnowledgeDiseaseDTO> records = universalPaging.getRecords();

		if(records.isEmpty()){
			return R.ok(universalPaging);
		}

		List<String> kgIdList = records.stream().map(KnowledgeDiseaseDTO::getId).collect(Collectors.toList());

//		//存在知识库字典映射的数据
//		List<BaseDictKgMapping> list = baseDictKgMappingService.list(Wrappers.<BaseDictKgMapping>lambdaQuery().in(BaseDictKgMapping::getKgId, kgIdList));
//		List<String> mappingKgIdList = list.stream().map(BaseDictKgMapping::getKgId).collect(Collectors.toList());
//		records.forEach(item -> {
//			if (CollUtil.isNotEmpty(mappingKgIdList)
//					&& mappingKgIdList.contains(item.getId())) {
//				item.setMappingFlag(CommonEnum.YesOrNoStr.YES.getType());
//			} else {
//				item.setMappingFlag(CommonEnum.YesOrNoStr.NO.getType());
//			}
//		});
//		batchTranslator.batchTranslate(universalPaging.getRecords());

		//特殊树结构字典转换
		for (KnowledgeDiseaseDTO record : universalPaging.getRecords()) {
			//树字典转值--药品类型--树结构的药品类型
			KgTag kgTag = treeConvert(record.getDrugType());
			if (null != kgTag) {
				record.setDrugType(kgTag.getName());
			}
			//检验分类--检查分类
			kgTag = treeConvert(record.getCheckType());
			if (null != kgTag) {
				record.setCheckType(kgTag.getName());
			}
			//手术分类
			kgTag = treeConvert(record.getTypeOfOperation());
			if (null != kgTag) {
				record.setTypeOfOperation(kgTag.getName());
			}
			//疾病分类
			kgTag = treeConvert(record.getClassificationOfDiseases());
			if (null != kgTag) {
				record.setClassificationOfDiseases(kgTag.getName());
			}
		}

		return R.ok(universalPaging);
	}

	private KgTag treeConvert(String key) {
		if (StringUtils.isNotBlank(key)) {
			KgTag tagById = kgTagService.getTagById(key);
			return tagById;
		}
		return null;
	}


	/**
	 * 疾病分页查询
	 *
	 * @param page        分页对象
	 * @param kgKnowledge 知识
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/diseasePagingQuery")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R diseasePagingQuery(@ParameterObject Page page, @ParameterObject KnowledgeListInParameter kgKnowledge) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeService.getDiseaseList(page, kgKnowledge));
	}


	/**
	 * 取新增项列表
	 *
	 * @param type 分类
	 * @return
	 */
	@Operation(summary = "取新增项列表", description = "取新增项列表")
	@GetMapping("/getsTheListOfNewItems")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<List<KgKnowledgeFieldVO>> getsTheListOfNewItems(String type) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeFieldService.getAListOfItems(type, true));
	}


	/**
	 * 单个知识内容查看（用作编辑）
	 *
	 * @param kgid 知识ID
	 * @return
	 */
	@Operation(summary = "单个知识内容查看", description = "单个知识内容查看")
	@GetMapping("/knowledgeContentViewing")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<List<KgKnowledgeFieldVO>> knowledgeContentViewing(String kgid) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeService.getsTheListOfNewItems(kgid));
	}


	/**
	 * 单个知识内容查看(用作预览)
	 *
	 * @param kgid 知识ID
	 * @return
	 */
	@Operation(summary = "单个知识内容查看(用作预览)", description = "单个知识内容查看(用作预览)")
	@GetMapping("/preview")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<KnowledgePreviewDTO> preview(String kgid) {
		KnowledgePreviewDTO previewData = kgKnowledgeService.getPreviewData(kgid);
		for (KgKnowledgeFieldVO kv : previewData.getBaseInfo()) {
			if (StringUtils.isNotBlank(kv.getFieldDataType()) && "treeSelect".equals(kv.getFieldDataType())) {
				//树字典转值
				KgTag tagById = kgTagService.getTagById(kv.getVal());
				if (null != tagById) {
					kv.setVal(tagById.getName());
				}
			}
		}

		for (KgKnowledgeFieldVO kgKnowledgeFieldVO : previewData.getRichText()) {
			String htmlContent = kgKnowledgeFieldVO.getVal();
			Document doc = Jsoup.parse(htmlContent);
			Elements images = doc.select("img");

			for (Element img : images) {
				String src = img.attr("src");
				if (src.indexOf("base64") >= 0) {
					continue;
				}
				try {
					URI imageUri = new URL(publicPicturePath + "/" + src).toURI().normalize();
					img.attr("src", imageUri.toString());
				} catch (Exception e) {
					log.error("路径路径转换出错: {}", src);
				}

			}
			kgKnowledgeFieldVO.setVal(doc.body().outerHtml().replace("<body>", "").replace("</body>", "").replaceAll("\n" +
					" ", "").replaceAll("\n", ""));

		}

		return R.ok(previewData);
	}


	/**
	 * 知识修改or知识新增
	 *
	 * @param knowledgeUpdateRequest 知识ID
	 * @return
	 */
	@Operation(summary = "知识修改or知识新增", description = "知识修改or知识新增")
	@PostMapping("/modifyAndAdd")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<Boolean> modifyAndAdd(@RequestBody KnowledgeUpdateRequest knowledgeUpdateRequest) {
		/*if (!CollectionUtil.isEmpty(knowledgeUpdateRequest.getList()) &&knowledgeUpdateRequest.getList().size() >1){
			todo 是否需要自己查询字典
		}*/

		Map<String, Map<String, SysDictItem>> dicType = new HashMap<>();
		knowledgeUpdateRequest.getList().stream().forEach(c -> {
			if (StringUtils.isNotBlank(c.getFieldDataType()) && c.getFieldDataType().equals("select")) {
				List<SysDictItem> list = sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictType, c.getFieldDict()));
				Map<String, SysDictItem> map = list.stream()
						.collect(Collectors.toMap(SysDictItem::getItemValue, obj -> obj));
				dicType.put(c.getFieldDict(), map);
			}
		});


//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeService.modifyAndAdd(knowledgeUpdateRequest.getKgid(), knowledgeUpdateRequest.getList(), dicType));
	}


	/**
	 * 删除知识
	 *
	 * @param kgid 知识ID
	 * @return
	 */
	@Operation(summary = "删除知识", description = "删除知识")
	@GetMapping("/deleteKnowledge")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
	public R<Boolean> deleteKnowledge(String kgid) {
//		LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeService.deleteKnowledge(kgid));
	}


	/*
	 *//**
	 * 分页查询
	 * @param page 分页对象
	 * @param kgKnowledge 知识
	 * @return
	 *//*
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
    public R getKgKnowledgePage(@ParameterObject Page page, @ParameterObject KgKnowledge kgKnowledge) {
        LambdaQueryWrapper<KgKnowledge> wrapper = Wrappers.lambdaQuery();
        return R.ok(kgKnowledgeService.page(page, wrapper));
    }


    *//**
	 * 通过id查询知识
	 * @param id id
	 * @return R
	 *//*
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_view')" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(kgKnowledgeService.getById(id));
    }

    *//**
	 * 新增知识
	 * @param kgKnowledge 知识
	 * @return R
	 *//*
    @Operation(summary = "新增知识" , description = "新增知识" )
    @SysLog("新增知识" )
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_add')" )
    public R save(@RequestBody KgKnowledge kgKnowledge) {
        return R.ok(kgKnowledgeService.save(kgKnowledge));
    }

    *//**
	 * 修改知识
	 * @param kgKnowledge 知识
	 * @return R
	 *//*
    @Operation(summary = "修改知识" , description = "修改知识" )
    @SysLog("修改知识" )
    @PutMapping
   // @PreAuthorize("@pms.hasPermission('admin_kgKnowledge_edit')" )
    public R updateById(@RequestBody KgKnowledge kgKnowledge) {
        return R.ok(kgKnowledgeService.updateById(kgKnowledge));
    }

    *//**
	 * 通过id删除知识
	 * @param ids id列表
	 * @return R
	 *//*
    @Operation(summary = "通过id删除知识" , description = "通过id删除知识" )
    @SysLog("通过id删除知识" )
    @DeleteMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_del')" )
    public R removeById(@RequestBody String[] ids) {
        return R.ok(kgKnowledgeService.removeBatchByIds(CollUtil.toList(ids)));
    }


    *//**
	 * 导出excel 表格
	 * @param kgKnowledge 查询条件
	 * @param ids 导出指定ID
	 * @return excel 文件流
	 * @ignore
	 *//*
    @ResponseExcel
    @GetMapping("/export")
   //@PreAuthorize("@pms.hasPermission('admin_kgKnowledge_export')" )
    public List<KgKnowledge> export(KgKnowledge kgKnowledge,String[] ids) {
        return kgKnowledgeService.list(Wrappers.lambdaQuery(kgKnowledge).in(ArrayUtil.isNotEmpty(ids), KgKnowledge::getId, ids));
    }*/
}
