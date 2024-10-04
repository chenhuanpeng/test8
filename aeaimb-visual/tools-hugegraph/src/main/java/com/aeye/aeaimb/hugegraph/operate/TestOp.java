package com.aeye.aeaimb.hugegraph.operate;

import com.aeye.aeaimb.hugegraph.config.HGFactory;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.driver.SchemaManager;
import org.apache.hugegraph.structure.graph.Vertex;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestOp {

	public static void main(String[] args) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("名称", "食欲减退");
		List<Vertex> vertex = HGFactory.ins().graph().listVertices("症状", properties);
		System.out.println(vertex.size());
	}

}
