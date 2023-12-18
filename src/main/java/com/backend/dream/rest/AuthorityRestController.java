package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.dto.AuthorityDTO;
import com.backend.dream.entity.Authority;
import com.backend.dream.repository.AccountRepository;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthorityRestController {
	@Autowired
	AuthorityService authorityService;

	@Autowired
	private AccountService accountService;

	@GetMapping()
	public List<Authority> getAuthorities(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			return authorityService.getAdmin();
		} else {
			return authorityService.findALL();
		}
	}
	
	@PostMapping()
	public Authority authority(@RequestBody Authority authority) {
		return authorityService.create(authority);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Long id) {
		authorityService.delete(id);
	}

	@GetMapping("/searchAccounts")
	public List<AccountDTO> searchAccountsByName(@RequestParam("name") String name) {
		return accountService.searchAccount(name);
	}

	@GetMapping("/filterByRole")
	public List<AccountDTO> getUsersByRole(@RequestParam("roleID") Long roleID) {
		return accountService.getUsersByRole(roleID);
	}


}
