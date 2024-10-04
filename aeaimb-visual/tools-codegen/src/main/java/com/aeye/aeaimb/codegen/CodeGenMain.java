package com.aeye.aeaimb.codegen;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.type.JdbcType;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CodeGenMain {

    /**
     * 数据库连接 , 输出目录
     */
    static String url = "jdbc:mysql://192.168.16.132/cdss_new";
    static String username = "root";
    static String password = "1qaz@WSX";
    static String outputDir = "d:/test";
    static String author =  System.getenv().get("USERNAME");
    static String parentPackage = "com.aeye.aeaimb";
    static String module = "cdss";
    static String xmlOutputDir = "d:/test/xml";

    public static void main(String[] args) {
        generator(args);
    }

    public static void generator(String[] args) {
		Map<OutputFile, String> pathInfo = new HashMap<>();
		pathInfo.put(OutputFile.xml, xmlOutputDir);
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author)
                            .outputDir(outputDir);
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            JdbcType jdbcType = metaInfo.getJdbcType();
                            int typeCode = jdbcType.TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent(parentPackage)
                                .moduleName(module)
                                .pathInfo(pathInfo)
//						.mapper("mapper.otpf")
					//	.entity("mapper.entity")
//						.entity("mapper.otpf.entity")

                )

                .strategyConfig(builder ->
                        builder.addInclude("medical_associations") // 设置需要生成的表名
//								.addInclude("cstn_meta")
//								.addInclude("cstn_operation_history")
//								.addInclude("cstn_path")
//								.addInclude("cstn_path_dept")
//								.addInclude("cstn_path_purpose")
//								.addInclude("cstn_purpose")
//								.addInclude("cstn_question")
//								.addInclude("cstn_question_option")
//								.addInclude("cstn_question_purpose")
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                .serviceBuilder()
                                    .disableService()
                                    .enableFileOverride()
                                .entityBuilder()
                                    .enableLombok()
								.enableColumnConstant()
									.addTableFills(new Column("create_time", FieldFill.INSERT),
											new Column("update_time", FieldFill.INSERT_UPDATE),
											new Column("create_by", FieldFill.INSERT),
											new Column("update_by", FieldFill.INSERT_UPDATE))
                                    .enableRemoveIsPrefix()
                                    .enableTableFieldAnnotation()
                                    .logicDeletePropertyName("deleteFlag")
                                    .logicDeleteColumnName("delete_flag")
                                    .idType(IdType.ASSIGN_ID)
                                    .enableFileOverride()
                                .mapperBuilder()
                                    .enableMapperAnnotation()
                                    .enableBaseColumnList()
                                    .enableBaseResultMap()
                                    .enableFileOverride()
                                .controllerBuilder()
                                    .enableRestStyle()
                                    .enableHyphenStyle()
                                    .enableFileOverride()


                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
