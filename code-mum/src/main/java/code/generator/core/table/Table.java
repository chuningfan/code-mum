package code.generator.core.table;

import java.util.List;
import java.util.Set;

import code.generator.core.TableElement;
import code.generator.core.column.Column;

public class Table extends TableElement{
	
	private List<Column> columns;

	private Set<TableRelationship> TableRelationships;
	
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public Set<TableRelationship> getTableRelationships() {
		return TableRelationships;
	}

	public void setTableRelationships(Set<TableRelationship> tableRelationships) {
		TableRelationships = tableRelationships;
	}
	
}
