package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgSynonyms implements Serializable {
    private static final long serialVersionUID = -41251159486708873L;

    private String objType;

    private String sourceName;

    private String targetName;

}

