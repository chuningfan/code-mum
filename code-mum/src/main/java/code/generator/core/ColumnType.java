package code.generator.core;

public class ColumnType {
	
	private String dbType;
	
	private boolean needLength;
	
	private boolean needScalar;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public boolean isNeedLength() {
		return needLength;
	}

	public void setNeedLength(boolean needLength) {
		this.needLength = needLength;
	}

	public boolean isNeedScalar() {
		return needScalar;
	}

	public void setNeedScalar(boolean needScalar) {
		this.needScalar = needScalar;
	}
	
}
