package code.generator.core.dto;

public class MappingInfo {
	
	private String dbSchema;
	
	private String tableName;
	
	private String javaDomainName;

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getJavaDomainName() {
		return javaDomainName;
	}

	public void setJavaDomainName(String javaDomainName) {
		this.javaDomainName = javaDomainName;
	}
	
}
