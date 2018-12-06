package code.generator.core.dto;

import java.util.Set;

import code.generator.core.constant.DBType;
import code.generator.core.constant.MapperType;

public class Request {

	private DBType type; 
	private String databaseURL; 
	private String batabaseName; 
	private String username; 
	private String password;
	private Set<MappingInfo> tableSet;
	private String domainPackageName;
	private String mapperPackageName;
	private MapperType mapperType;
	private String targetProject;
	
	
	public DBType getType() {
		return type;
	}
	public void setType(DBType type) {
		this.type = type;
	}
	public String getDatabaseURL() {
		return databaseURL;
	}
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}
	public String getBatabaseName() {
		return batabaseName;
	}
	public void setBatabaseName(String batabaseName) {
		this.batabaseName = batabaseName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<MappingInfo> getTableSet() {
		return tableSet;
	}
	public void setTableSet(Set<MappingInfo> tableSet) {
		this.tableSet = tableSet;
	}
	public String getDomainPackageName() {
		return domainPackageName;
	}
	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}
	public String getMapperPackageName() {
		return mapperPackageName;
	}
	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}
	public MapperType getMapperType() {
		return mapperType;
	}
	public void setMapperType(MapperType mapperType) {
		this.mapperType = mapperType;
	}
	public String getTargetProject() {
		return targetProject;
	}
	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
	}
	
}
