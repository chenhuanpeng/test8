package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 图谱标准字典映射
 * </p>
 *
 * @author huangzhengri
 * @since 2024-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgGraphMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 图谱编码
     */
    private String graphCode;

    /**
     * 图谱名称
     */
    private String graphName;

    /**
     * 标准编码
     */
    private String standCode;

    /**
     * 标准名称
     */
    private String standName;

    /**
     * 字典分类: 疾病、手术、检查、检验、药品
     */
    private String mediDictType;


}
