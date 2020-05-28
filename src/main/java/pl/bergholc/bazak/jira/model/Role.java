package pl.bergholc.bazak.jira.model;

public enum Role {
	MANAGER(1), EMPLOYEE(2), CUSTOMER(3);
	int value;

	Role(int value){
		this.value = value;
	}

}
