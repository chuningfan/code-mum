package code.generator.core.constant;

public enum DBType {
	
	SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://"), 
	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://"); 
	
	private String driverClassName;
	
	private String connectionPrefix;
	

	private DBType(String driverClassName, String connectionPrefix) {
		this.driverClassName = driverClassName;
		this.connectionPrefix = connectionPrefix;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getConnectionPrefix() {
		return connectionPrefix;
	}

	public void setConnectionPrefix(String connectionPrefix) {
		this.connectionPrefix = connectionPrefix;
	}
	
}
