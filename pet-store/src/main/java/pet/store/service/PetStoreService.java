package pet.store.service;



import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

//This package will serve as a link between the main application and the DAO connection packages.

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhoNum(petStoreData.getPetStorePhoNum());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if(Objects.isNull(petStoreId)) {
			return new PetStore();
		}
		else {
			return findPetStoreById(petStoreId);
		}
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID = " + petStoreId + " was not found."));
	}

	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		copyEmployeeFields(petStore, employee, petStoreEmployee);
		employee.setPetStore(petStore);
		
		Employee dbEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(dbEmployee);
	}

	private void copyEmployeeFields(PetStore petStore, Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhoNum(petStoreEmployee.getEmployeePhoNum());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		petStore.getEmployees().add(employee);
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(petStoreId, employeeId);
		}
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee =  employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException(
						"Employee with ID = " + employeeId + " was not found."));
		
		if(employee.getPetStore().getPetStoreId() == petStoreId) {
			return employee;
		} else {
			throw new IllegalArgumentException("Please enter correct data in the appropriate format");
		}
	}

	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);
		copyCustomerFields(petStore, customer, petStoreCustomer);
		
		Customer dbCustomer = customerDao.save(customer);
		customer.getPetStore().add(petStore);
		dbCustomer.getPetStore().add(petStore);
		return new PetStoreCustomer(dbCustomer);
	}

	private Set<PetStore> listOfPetStoresById(Long petStoreId) {
		List<PetStore> petStores = petStoreDao.findAll();
		Set<PetStore> setPetStores = new HashSet<>();
		
		for(PetStore tempStore : petStores) {
			if(tempStore.getPetStoreId() == petStoreId) {
				setPetStores.add(tempStore);
			}
		}
		
		return setPetStores;
	}

	private void copyCustomerFields(PetStore petStore, Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
		petStore.getCustomers().add(customer);
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;
		if(Objects.isNull(customerId)) {
			customer = new Customer();
			customer.setPetStore(listOfPetStoresById(petStoreId));
		} else {
			customer = findCustomerById(customerId);
			for(PetStore petStores : customer.getPetStore()) {
				if(petStores.getPetStoreId() == petStoreId) {
					customer.getPetStore().add(petStores);
				}
			}
		}
		
		return customer;
	}

	private Customer findCustomerById(Long customerId) {
		return customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + " was not found."));
		
	}

	@Transactional(readOnly = false)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			
			result.add(psd);			
		}
	
		return result;
	}

	@Transactional(readOnly = false)
	public PetStoreData retrivePetStoreById(Long petStoreId) {
		PetStore petStore = petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID = " + petStoreId + " was not found."));
		
		return new PetStoreData(petStore);
	}

	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
		
	}

	public void deleteAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		
		for(PetStore petStore : petStores) {
			petStoreDao.delete(petStore);
		}
		
	}

	public void deleteAllCustomers() {
		List<Customer> customers = customerDao.findAll();
		
		for(Customer customer : customers) {
			customerDao.delete(customer);
		}
	}
		
}
