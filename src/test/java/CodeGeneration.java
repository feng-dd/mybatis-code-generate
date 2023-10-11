import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * @Description:
 * @Author:murphy
 * @Date:2023/6/15 17:06
 **/
@SpringBootTest
public class CodeGeneration {
    @Test
    public void generation() {

        /*========= 数据库配置 ===========*/
        String url = "jdbc:mysql://121.40.196.204:28308/test_spiderw_cfg_584";
        String url_suffix = "?zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
        String userName = "eversight";
        String password = "ADF@23dss";
        /* 配置项 */
        // 项目路径
        String projectPath = System.getProperty("user.dir");
        // java下的文件路径
        String filePath = projectPath + "/src/main/java";
        String packageName = "com.zhss.job";

        // 创建一个代码生成器
        FastAutoGenerator.create(url + url_suffix, userName, password)
                //全局配置(GlobalConfig)
                .globalConfig(builder -> {
                    builder.author("murphy") // 设置作者，可以写自己名字
                            //.enableSwagger() // 开启 swagger 模式，这个是接口文档生成器，如果开启的话，就还需要导入swagger依赖
                            .dateType(DateType.TIME_PACK)//时间策略
                            .commentDate("yyyy-MM-dd") //注释日期
                            .outputDir(filePath) // 指定输出目录，一般指定到java目录
                            .fileOverride(); // 覆盖已生成文件
                })
                //包配置(PackageConfig)
                .packageConfig(builder -> {
                    builder.parent(packageName) // 设置父包名
                            .moduleName("") // 设置父包模块名，这里一般不设置
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径，这里是Mapper配置文件的路径，建议使用绝对路径
                })
                //策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    builder
//                            .addInclude(); // 添加表匹配，指定要生成的数据表名，不写默认选定数据库所有表
                            .addInclude("zhss_result_zb"); // 设置需要生成的表名
//                            .addInclude("zhss_result_data") // 设置需要生成的表名
//                            .addInclude("zhss_jgb");// 设置需要生成的表名
//                            .addInclude("tbl_lost") // 设置需要生成的表名
//                            .addInclude("tbl_school") // 设置需要生成的表名
//                            .addInclude("tbl_status") // 设置需要生成的表名
//                            .addInclude("tbl_user") // 设置需要生成的表名
//                            .addTablePrefix("tbl_"); // 设置过滤表前缀
                    builder.entityBuilder() //实体策略配置
                            //.disableSerialVersionUID()禁用生成SerialVersionUID：默认true
                            .enableChainModel()//开启链式模型
                            .enableLombok()//开启lombok
                            .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀
                            .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                            //.addTableFills()添加表字段填充
                            .naming(NamingStrategy.underline_to_camel)//数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                            .columnNaming(NamingStrategy.underline_to_camel)//表字段映射实体属性命名规则：默认null，不指定按照naming执行
                            .idType(IdType.AUTO)//添加全局主键类型
                            .formatFileName("%s");//格式化实体名称，%s取消首字母I
                    builder.serviceBuilder()
//                            .formatServiceFileName("%sService") //设置service的命名策略,没有这个配置的话，生成的service和serviceImpl类前面会有一个I，比如IUserService和IUserServiceImpl
                            .formatServiceImplFileName("%sServiceImpl"); //设置serviceImpl的命名策略
                    builder.controllerBuilder()
                            .enableRestStyle(); // 开启生成@RestController 控制器，不配置这个默认是Controller注解，RestController是返回Json字符串的，多用于前后端分离项目。
                    builder.mapperBuilder()
                            .enableMapperAnnotation()//开启 @Mapper 注解，也就是在dao接口上添加一个@Mapper注解，这个注解的作用是开启注解模式，就可以在接口的抽象方法上面直接使用@Select和@Insert和@Update和@Delete注解。
                            .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                            .enableBaseColumnList()//启用xml文件中的BaseColumnList
                            //.cache(缓存类.class)设置缓存实现类
//                            .formatMapperFileName("%sMapper")//格式化Dao类名称
                            .formatXmlFileName("%sMapper");//格式化xml文件名称

                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new VelocityTemplateEngine())
                .execute(); //执行以上配置
    }
}
