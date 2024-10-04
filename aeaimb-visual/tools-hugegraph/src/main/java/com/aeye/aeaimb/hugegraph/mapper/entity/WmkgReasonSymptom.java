package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.omg.CORBA.IDLType;

/**
 * 推理症状(WmkgReasonSymptom)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonSymptom implements Serializable {
	private static final long serialVersionUID = 288177990844820325L;
	/**
	 * 症状编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String symptomCode;
	/**
	 * 症状名称
	 */
	private String symptomName;


}

