package com.aeye.aeaimb.mkpb.controller.wmkg;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.constant.CacheConstants;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDict;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDictItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDictItemService;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDictService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 基础字典
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
@RestController
@RequestMapping("wmkgDict")
public class WmkgDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDictService wmkgDictService;

	@Resource
	private WmkgDictItemService wmkgDictItemService;


	/**
	 * 通过ID查询字典信息
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/details/{id}")
	public R getById(@PathVariable Long id) {
		return R.ok(wmkgDictService.getById(id));
	}

	/**
	 * 查询字典信息
	 * @param query 查询信息
	 * @return 字典信息
	 */
	@GetMapping("/details")
	public R getDetails(@ParameterObject WmkgDict query) {
		return R.ok(wmkgDictService.getOne(Wrappers.query(query), false));
	}

	/**
	 * 分页查询字典信息
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	public R<IPage> getDictPage(@ParameterObject Page page, @ParameterObject WmkgDict wmkgDict) {
		return R.ok(wmkgDictService.page(page,
				Wrappers.<WmkgDict>lambdaQuery()
						.eq(StrUtil.isNotBlank(wmkgDict.getSystemFlag()), WmkgDict::getSystemFlag, wmkgDict.getSystemFlag())
						.like(StrUtil.isNotBlank(wmkgDict.getDictType()), WmkgDict::getDictType, wmkgDict.getDictType())));
	}

	/**
	 * 通过字典类型查找字典
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/type/{type}")
	public R<List<WmkgDictItem>> getDictByType(@PathVariable String type) {
		List<WmkgDictItem> list = wmkgDictItemService.list(Wrappers.<WmkgDictItem>query().lambda().eq(WmkgDictItem::getDictType, type));
		list.forEach(item -> item.setValue(item.getItemValue()));
		return R.ok(list);
	}

	/**
	 * 添加字典
	 * @param wmkgDict 字典信息
	 * @return success、false
	 */
	@SysLog("添加字典")
	@PostMapping
	public R save(@Valid @RequestBody WmkgDict wmkgDict) {
		wmkgDictService.save(wmkgDict);
		return R.ok(wmkgDict);
	}

	/**
	 * 删除字典，并且清除字典缓存
	 * @param ids ID
	 * @return R
	 */
	@SysLog("删除字典")
	@DeleteMapping
	public R removeById(@RequestBody String[] ids) {
		return R.ok(wmkgDictService.removeDictByIds(ids));
	}

	/**
	 * 修改字典
	 * @param wmkgDict 字典信息
	 * @return success/false
	 */
	@PutMapping
	@SysLog("修改字典")
	public R updateById(@Valid @RequestBody WmkgDict wmkgDict) {
		return wmkgDictService.updateDict(wmkgDict);
	}

	/**
	 * 分页查询
	 * @param name 名称或者字典项
	 * @return
	 */
	@GetMapping("/list")
	public R getDictList(String name) {
		return R.ok(wmkgDictService.list(Wrappers.<WmkgDict>lambdaQuery()
				.like(StrUtil.isNotBlank(name), WmkgDict::getDictType, name)
				.or()
				.like(StrUtil.isNotBlank(name), WmkgDict::getDescription, name)));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysDictItem 字典项
	 * @return
	 */
	@GetMapping("/item/page")
	public R getSysDictItemPage(Page page, WmkgDictItem sysDictItem) {
		return R.ok(wmkgDictItemService.page(page, Wrappers.query(sysDictItem)));
	}

	/**
	 * 通过id查询字典项
	 * @param id id
	 * @return R
	 */
	@GetMapping("/item/details/{id}")
	public R getDictItemById(@PathVariable("id") Long id) {
		return R.ok(wmkgDictItemService.getById(id));
	}

	/**
	 * 查询字典项详情
	 * @param query 查询条件
	 * @return R
	 */
	@GetMapping("/item/details")
	public R getDictItemDetails(WmkgDictItem query) {
		return R.ok(wmkgDictItemService.getOne(Wrappers.query(query), false));
	}

	/**
	 * 新增字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@SysLog("新增字典项")
	@PostMapping("/item")
	public R save(@RequestBody WmkgDictItem sysDictItem) {
		return R.ok(wmkgDictItemService.save(sysDictItem));
	}

	/**
	 * 修改字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@SysLog("修改字典项")
	@PutMapping("/item")
	public R updateById(@RequestBody WmkgDictItem sysDictItem) {
		return wmkgDictItemService.updateDictItem(sysDictItem);
	}

	/**
	 * 通过id删除字典项
	 * @param id id
	 * @return R
	 */
	@SysLog("删除字典项")
	@DeleteMapping("/item/{id}")
	public R removeDictItemById(@PathVariable String id) {
		return wmkgDictItemService.removeDictItem(id);
	}


	@ResponseExcel
	@Ignore
	@GetMapping("/export")
	public List<WmkgDictItem> export(WmkgDictItem sysDictItem) {
		return wmkgDictItemService.list(Wrappers.query(sysDictItem));
	}

}

