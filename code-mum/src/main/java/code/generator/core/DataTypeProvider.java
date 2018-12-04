package code.generator.core;

import java.util.Map;

import code.generator.core.constant.DBType;

public interface DataTypeProvider {
	
	//Map<String: javaType, ColumnType>
	Map<String, ColumnType> getDataType(DBType db);
	
}
