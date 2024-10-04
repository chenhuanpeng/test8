package com.aeye.aeaimb.hugegraph.operate;

import com.aeye.aeaimb.hugegraph.config.HGFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.driver.SchemaManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CleanGraphOp {

	public static void cleanGraph() {
		HugeClient hugeClient = HGFactory.ins();
		SchemaManager schema = hugeClient.schema();

		schema.getEdgeLabels().forEach(edgeLabel -> {
			try {
				schema.removeEdgeLabel(edgeLabel.name());
				log.info("删除边: " + edgeLabel.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		schema.getVertexLabels().forEach(vertexLabel -> {
			try {
				schema.removeVertexLabel(vertexLabel.name());
				log.info("删除顶点: " + vertexLabel.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		schema.getIndexLabels().forEach(indexLabel -> {
			try {
				schema.removeEdgeLabel(indexLabel.name());
				log.info("删除索引: " + indexLabel.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		schema.getPropertyKeys().forEach(propertyKey -> {
			try {
				schema.removePropertyKey(propertyKey.name());
				log.info("删除属性: " + propertyKey.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});


	}

	public static void main(String[] args) {
		cleanGraph();
	}

}
