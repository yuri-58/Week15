package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {

	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private Integer petStoreZip;
	private Integer petStorePhoNum;
	
	Set<PetStoreCustomer> customers = new HashSet<PetStoreCustomer>();
	Set<PetStoreEmployee> employees = new HashSet<PetStoreEmployee>();

	public PetStoreData(PetStore save) {
		 petStoreId = save.getPetStoreId();
		 petStoreName = save.getPetStoreName();
		 petStoreAddress = save.getPetStoreAddress();
		 petStoreCity = save.getPetStoreCity();
		 petStoreState = save.getPetStoreState();
		 petStoreZip = save.getPetStoreZip();
		 petStorePhoNum = save.getPetStorePhoNum();
		
		for (Customer customer : save.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));
		}
		
		for (Employee employee : save.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));
		}
	}

	

	

	
	
}
