package com.aeye.aeaimb.mkpb.vo.wmkg;

import lombok.Data;

import java.io.Serializable;


@Data
public class WmkgSynonymsVO implements Serializable {
    private static final long serialVersionUID = -41251159486708873L;

    private String objType;

    private String sourceName;

    private String targetName;

}

