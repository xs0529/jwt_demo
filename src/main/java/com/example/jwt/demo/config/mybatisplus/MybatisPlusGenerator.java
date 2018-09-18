package com.example.jwt.demo.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class MybatisPlusGenerator {

    public static void main(String[] args) {

        // 项目路径
        String PATH = "src/main/java";
        // 包路径
        String PACKAGE1 = "com.example.jwt.demo.user";
        // 作者
        String AUTHOR = "谢霜";
        // 数据库用户名
        String USERNAME = "root";
        // 数据库密码
        String PASSWORD = "root";
        // 数据库名
        String DATABASE = "mydb";
        // 生成策略，ture：按照表名生成，false：按照表前缀生成
        Boolean B = true;
        // 需要生成的表
        String[] tableName = new String[] { "user","role","user_role","permission","role_permission" };
        // 需要生成的表前缀
        String[] tableQ = new String[]{"t"};
        // 是否生成 Controller CRUD代码
        Boolean A = false;
        // 是否使用 lombok
        Boolean C = true;


        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setSwagger2(true);
        gc.setOutputDir(PATH);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(AUTHOR);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/"+DATABASE+"?characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        if (B){
            strategy.setInclude(tableName); // 需要生成的表
        }else {
            strategy.setTablePrefix(tableQ);// 此处可以修改为您的表前缀
        }
        strategy.setEntityLombokModel(C);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        mpg.setStrategy(strategy);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 调整 xml 生成目录演示
        /*focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "mybatis-demo/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });*/
        if (A){
            TemplateConfig tc = new TemplateConfig();
            tc.setController("/templates/generate/controller.java.vm");
            mpg.setTemplate(tc);
        }
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE1);
        mpg.setPackageInfo(pc);
        mpg.execute();
    }
}
