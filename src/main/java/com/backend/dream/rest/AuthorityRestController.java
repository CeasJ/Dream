package com.backend.dream.rest;

import com.backend.dream.entity.Authority;
import com.backend.dream.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthorityRestController {
	@Autowired
	AuthorityService authorityService;

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

	@GetMapping("/download")
	private ResponseEntity<InputStreamResource> download() throws IOException {
		String fileName ="Data-authorities.xlsx";
		ByteArrayInputStream inputStream = authorityService.getdataAuthority();
		InputStreamResource response = new InputStreamResource(inputStream);

		ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
		return responseEntity;
	}
}
