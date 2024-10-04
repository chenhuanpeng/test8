//package com.aeye.aeaimb.codegen;
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//import java.util.ArrayList;
//
//public class CodeGenerator {
//    /**
//     * 数据库连接 , 输出目录
//     */
//    static String url = "jdbc:mysql://192.168.16.132/cdss20240730?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
//    static String username = "root";
//    static String password = "1qaz@WSX";
//    static String outputDir = "e:/test";
//    static String author =  System.getenv().get("USERNAME");
//    static String parentPackage = "com.aeye.aeaimb";
//    static String module = "cdss";
//    static String xmlOutputDir = "e:/test/xml";
//
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setOutputDir(outputDir);
//        gc.setFileOverride(true);
//        gc.setOpen(false);
//        gc.setAuthor(author);
//        gc.setIdType(IdType.ASSIGN_ID);
//        gc.setBaseResultMap(true);
//        gc.setBaseColumnList(true);
////        gc.setSwagger2(true);
//        gc.setServiceName("%sService");
//        gc.setDateType(DateType.TIME_PACK);
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl(url);
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername(username);
//        dsc.setPassword(password);
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent(parentPackage);
//        pc.setModuleName(module);
//        pc.setMapper("mapper");
//        pc.setXml("mapper.xml");
//        pc.setEntity("mapper.entity");
//        pc.setService("service");
//        pc.setServiceImpl("service.impl");
//        pc.setController("controller");
//        mpg.setPackageInfo(pc);
//
//        // 策略配置
//        StrategyConfig sc = new StrategyConfig();
//        sc.setNaming(NamingStrategy.underline_to_camel);
//        sc.setColumnNaming(NamingStrategy.underline_to_camel);
//        sc.setEntityLombokModel(true);
//        sc.setRestControllerStyle(true);
//        sc.setControllerMappingHyphenStyle(true);
//        sc.setLogicDeleteFieldName("del_flag");
//
//        //设置自动填充配置
//        TableFill gmt_create = new TableFill("create_time", FieldFill.INSERT);
//        TableFill gmt_modified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> tableFills=new ArrayList<TableFill>();
//        tableFills.add(gmt_create);
//        tableFills.add(gmt_modified);
//        sc.setTableFillList(tableFills);
//        sc.setEntityBooleanColumnRemoveIsPrefix(true);
//
//        //乐观锁
//        sc.setVersionFieldName("version");
//
//        // 包含的表名, 支持正则表达式
//        sc.setInclude("raqc_rich_reason");
//        sc.setRestControllerStyle(true);
//        sc.setColumnNaming(NamingStrategy.underline_to_camel);
//        sc.setControllerMappingHyphenStyle(false);
//
//        mpg.setStrategy(sc);
//
//        // 生成代码
//        mpg.execute();
//    }
//
//}