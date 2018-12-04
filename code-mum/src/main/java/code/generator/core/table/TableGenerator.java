package code.generator.core.table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import code.generator.core.Generator;
import code.generator.core.column.Column;
import code.generator.core.column.ColumnGenerator;
import code.generator.core.entity.EntityGenerator;

public class TableGenerator implements Generator<Table> {

	private static final String CREATE = "CREATE TABLE ${tableName} (";

	private static final String END = ");";
	
	private static final String TABLE_COMMENT = "COMMENT ON TABLE ${tableName} is ";

	private static final ColumnGenerator CG = new ColumnGenerator();

	private static final EntityGenerator EG = new EntityGenerator();
	
	private static final String FILE_PATH = "";

	public String doGenerate(Table... elements) {
		StringBuffer buffer = new StringBuffer();
		for (Table t : elements) {
			String comment = t.getComment();
			String tableName = t.getName();
			Set<TableRelationship> relationships = t.getTableRelationships();
			String columnSql = CG.doGenerate(t.getColumns().toArray(new Column[t.getColumns().size()]));
			if (Objects.nonNull(relationships) && !relationships.isEmpty()) {
				// TODO
			}
			buffer.append(resolveTableName(CREATE, tableName) + columnSql + END + resolveTableName(TABLE_COMMENT, tableName) + comment + "; ");
			String entityStr = EG.doGenerate(t);
			String className = getClassNameByTableName(tableName);
			createJavaClass(className, FILE_PATH, entityStr);
		}
		executeSql(buffer.toString());
		return buffer.toString();
	}

	private String resolveTableName(String str, String val) {
		return str.replace("${tableName}", val);
	}
	
	private String getClassNameByTableName(String tableName) {
		String[] array = tableName.split("_");
		StringBuffer res = new StringBuffer();
		for (String str: array) {
			res.append(upperFirstLetter(str));
		}
		return res.toString();
	}
	
	private String upperFirstLetter(String str) {
		String first = str.substring(0, 1);
		String other = str.substring(1);
		return first.toUpperCase() + other;
	}
	
	public static void main(String[] args) {
		String s = "hello";
		System.out.println(s.toUpperCase(Locale.ENGLISH));
	}
	
	private void createJavaClass(String className, String filePath, String source) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        FileWriter writer = null;
        try {
        	writer = new FileWriter(new File(dir, className + ".java"));
            writer.write(source);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> it = javaFileManager.getJavaFileObjects(new File(dir,className + ".java"));
        CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./" + filePath), null, it);
        task.call();
        try {
			javaFileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

	private void executeSql(String sql) {

	}

}
