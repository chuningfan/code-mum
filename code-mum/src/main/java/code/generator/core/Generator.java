package code.generator.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.google.common.collect.Lists;

import code.generator.core.constant.DBType;
import code.generator.core.dto.MappingInfo;
import code.generator.core.dto.Request;

public class Generator {
	
	private static final ThreadLocal<String> POOL = new ThreadLocal<String>() {

		@Override
		protected String initialValue() {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
					"<!DOCTYPE generatorConfiguration " +
					"PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\" " + 
					"\"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\"> " + 
					"<generatorConfiguration> " + 
					    "<context id=\"generator\" targetRuntime=\"MyBatis3\" defaultModelType=\"flat\"> " + 
					        "<commentGenerator> " +
					            " <property name=\"suppressAllComments\" value=\"true\" /> " +
					        "</commentGenerator> " +
					        "<jdbcConnection " +  
					            "driverClass=\"${driverClass}\" " +
					            "connectionURL=\"${databaseURL}\" " + 
					            "userId=\"${username}\" " +
					            "password=\"${password}\"> " + 
					        "</jdbcConnection> " +
					        "<javaTypeResolver> " + 
					            "<property name=\"forceBigDecimals\" value=\"true\" /> " + 
					        "</javaTypeResolver> " +
					        "<javaModelGenerator targetPackage=\"${domainPackageName}\" targetProject=\"${targetProject}\"> " +
					            "<property name=\"enableSubPackages\" value=\"false\" /> " + 
					            "<property name=\"trimStrings\" value=\"true\" /> " + 
					        "</javaModelGenerator> " +
					        "<sqlMapGenerator targetPackage=\"${mapperPackageName}\"  targetProject=\"${targetProject}\"> " + 
					            "<property name=\"enableSubPackages\" value=\"false\" /> " + 
					        "</sqlMapGenerator> " + 
					        "<javaClientGenerator type=\"${mapperType}\" targetPackage=\"${mapperPackageName}\"  targetProject=\"${targetProject}\"> " + 
					            "<property name=\"enableSubPackages\" value=\"false\" /> " +
					        "</javaClientGenerator> " + 
					        "${tableInfoList}" +
					    "</context> " +
					"</generatorConfiguration>";
		}
		
	};
	
	public static final void doGenerate(Request request) throws Exception {
		validate(request);
		List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String template = POOL.get();
        template = resolve(template, request);
        ByteArrayInputStream bis = new ByteArrayInputStream(template.getBytes());
        InputStream in = new BufferedInputStream(bis);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(in);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(new ProgressCallback() {
			public void introspectionStarted(int totalTasks) {
			}
			public void generationStarted(int totalTasks) {
				System.out.println("Start processing " + totalTasks + " tasks.");
			}
			public void saveStarted(int totalTasks) {
			}
			public void startTask(String taskName) {
			}
			public void done() {
				System.out.println("Job's done ...");
			}
			public void checkCancel() throws InterruptedException {
			}
        });
	}
	
	private static void validate(Request request) throws Exception {
		if (request == null || hasEmptyPropery(request)) {
			throw new Exception("Invalid reqeust!");
		}
		for (MappingInfo info: request.getTableSet()) {
			if (hasEmptyPropery(info, "dbSchema")) {
				throw new Exception("Invalid reqeust!");
			}
		}
	}

	private static String resolve(String template, Request request) throws Exception {
		template = template.replace("${driverClass}", request.getType().getDriverClassName());
		String url = request.getType().getConnectionPrefix() + request.getDatabaseURL();
		String batabase = "";
		if (request.getType() == DBType.SQLSERVER) {
			batabase = ";databaseName=" + request.getBatabaseName();
		} else {
			batabase = "/" + request.getBatabaseName();
		}
		url += batabase;
		template = template.replace("${databaseURL}", url);
		template = template.replace("${username}", request.getUsername());
		template = template.replace("${password}", request.getPassword());
		template = template.replace("${domainPackageName}", request.getDomainPackageName());
		template = template.replace("${mapperPackageName}", request.getMapperPackageName());
		template = template.replace("${targetProject}", request.getTargetProject());
		template = template.replace("${mapperType}", request.getMapperType().toString());
		Set<MappingInfo> set = request.getTableSet();
		StringBuffer buffer = new StringBuffer();
		for(MappingInfo info: set) {
			String schema = info.getDbSchema() == null || "".equals(info.getDbSchema().trim()) ? "" : "schema=\"" + info.getDbSchema() + "\"";
			String tableName = info.getTableName();
			String javaDomainName = info.getJavaDomainName();
			buffer.append("<table domainObjectName=\"" + javaDomainName + "\" tableName=\"" + tableName + "\" " + schema + "></table>");
		}
		template = template.replace("${tableInfoList}", buffer.toString());
		return template;
	}

	public static final boolean hasEmptyPropery(Object obj, String...exceptProperties) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> excpetNames = null;
		if (exceptProperties != null) {
			excpetNames = Lists.newArrayList(exceptProperties);
		}
		boolean flag = false;
		for (Field f: fields) {
			if (excpetNames != null) {
				if (excpetNames.contains(f.getName())) {
					continue;
				}
			}
			f.setAccessible(true);
			Object val = f.get(obj);
			if (null == val) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
}
