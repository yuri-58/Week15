package pet.store.controller.model;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
	
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	private PetStore petStore;
	private Set<PetStoreCustomer> petStoreCustomer;

	public PetStoreCustomer(Customer customer) {
		customerId = customer.getCustomerId();
		  customerFirstName = customer.getCustomerFirstName();
		  customerLastName = customer.getCustomerLastName();
		  customerEmail = customer.getCustomerEmail();
	}

	
}

