package com.aeye.aeaimb.common.dtos.aggregation;

import java.util.*;

public class FourQuestionProcessRepo {
	private static Set<TcmFourDiagQuestion.ProcessItem> processItemList = new LinkedHashSet<>();

	static{
		processItemList.clear();

		processItemList.add(new TcmFourDiagQuestion.ProcessItem("0","主诉"));
		processItemList.add(new TcmFourDiagQuestion.ProcessItem("10","刻下症"));
		processItemList.add(new TcmFourDiagQuestion.ProcessItem("20","体格检查"));
//		processItems.add(new ProcessItem("30","即往史"));
		processItemList.add(new TcmFourDiagQuestion.ProcessItem("40","其他"));
	}

	public static Set<TcmFourDiagQuestion.ProcessItem> getProcessItems(){
		return processItemList;
	}
}
