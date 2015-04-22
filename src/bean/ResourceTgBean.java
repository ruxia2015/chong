package bean;

import annotation.SQLAnnotation;

public class ResourceTgBean {
	/**
	 * id
	 */

	@SQLAnnotation(update_is_where = true)
	private String id;

	private String tgId;

	private String resourceId;

	private String account;

	private String password;

	private String email;

	private String tgDomain;

	@SQLAnnotation(is_column = false, where_oper = "in", where_column = "resourceId")
	private String resourceIds;


	@SQLAnnotation(is_Ingore = true)
	private String resourceDomain;
	@SQLAnnotation(is_Ingore = true)
	private String registerState;
	
	
	
	

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}



	public String getResourceDomain() {
		return resourceDomain;
	}

	public void setResourceDomain(String resourceDomain) {
		this.resourceDomain = resourceDomain;
	}

	public String getRegisterState() {
		return registerState;
	}

	public void setRegisterState(String registerState) {
		this.registerState = registerState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTgId() {
		return tgId;
	}

	public void setTgId(String tgId) {
		this.tgId = tgId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTgDomain() {
		return tgDomain;
	}

	public void setTgDomain(String tgDomain) {
		this.tgDomain = tgDomain;
	}

}
