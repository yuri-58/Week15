package pet.store.controller.model;


import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {

	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private Integer employeePhoNum;
	private String employeeJobTitle;
	
	Set<PetStoreEmployee> petStoreEmployee;

	public PetStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhoNum = employee.getEmployeePhoNum();
		employeeJobTitle = employee.getEmployeeJobTitle();
		
	}
}
