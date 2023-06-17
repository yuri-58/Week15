package pet.store.controller.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreControler {
	
	private Map<String, String> messageInput(){
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("message", "Deleted!");
		
		return msg;
	}

	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/new")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData savePetStore(
			@RequestBody PetStoreData petStoreData) {
		log.info("Created Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
		
	@PostMapping("/{petStoreId}/employee")
	public PetStoreEmployee savePetStoreEmployee(@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Created Employee", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	
	@PostMapping("/{petStoreId}/customer")
	public PetStoreCustomer savePetStoreCustomer(@PathVariable Long petStoreId, 
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Created Customer", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@PutMapping("/{petStoreId}/employee")
	public PetStoreEmployee updatePetStoreEmployee(@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee petStoreEmployee,
			@RequestBody PetStore petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	
	@PutMapping("/{petStoreId}/customer")
	public PetStoreCustomer updatePetStoreCustomer(@PathVariable Long petStoreId, 
			@RequestBody PetStoreCustomer petStoreCustomer) {
		petStoreCustomer.getPetStore().setPetStoreId(petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStoreInformation(@PathVariable Long petStoreId, 
		@RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@GetMapping
	public List<PetStoreData> listAllPetStores(){
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/{petStoreId}")
	public PetStoreData getPetStore(@PathVariable Long petStoreId) {
		return petStoreService.retrivePetStoreById(petStoreId);
	}
	
	@DeleteMapping("/delete/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		petStoreService.deletePetStoreById(petStoreId);
		Map<String, String> msg = messageInput();
		return msg;		
		
	}
	
	@DeleteMapping("/delete_all_pet_stores")
	public Map<String, String> deleteAllPetStores() {
		petStoreService.deleteAllPetStores();
		Map<String, String> msg = messageInput();
		return msg;
		
	}
	
	@DeleteMapping("/delete_all_customers")
	public Map<String, String> deleteAllCustomers() {
		petStoreService.deleteAllCustomers();
		Map<String, String> msg = messageInput();
		return msg;
		
	}
		
}
