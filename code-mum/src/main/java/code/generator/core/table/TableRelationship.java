package code.generator.core.table;

import code.generator.core.constant.TableRelation;

public class TableRelationship {
	
	private String tableName;
	
	private String columnName;
	
	private TableRelation relation;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public TableRelation getRelation() {
		return relation;
	}

	public void setRelation(TableRelation relation) {
		this.relation = relation;
	}
	
	
	
}
