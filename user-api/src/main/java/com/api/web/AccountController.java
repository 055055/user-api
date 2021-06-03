package com.api.web;

import com.api.common.config.JwtTokenProcess;
import com.api.service.AccountService;
import com.api.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(value = "/v1/user")
@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
	private final JwtTokenProcess jwtTokenProcess;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(@RequestBody AccountLoginReqDto req) {
		AccountLoginResDto userDto = accountService.login(req);
		return jwtTokenProcess.createToken(userDto.getEmail(), userDto.getRole().getRoleName());
	}

	@GetMapping
	public List<AccountDto> findAll() {
		return accountService.findAll();
	}

	@GetMapping(value = "/{seq}")
	public AccountDto findBySeq(@PathVariable Long seq) {
		return accountService.findyBySeq(seq);
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody AccountSaveReqDto req) {
		AccountSaveResDto result = accountService.save(req);
		return new ResponseEntity(result, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{seq}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long seq) {
		accountService.delete(seq);
	}

	@PatchMapping(value = "/{seq}")
	public AccountUpdateResDto update(@RequestBody AccountUpdateReqDto req,
		@PathVariable Long seq) {
		return accountService.update(seq, req);
	}
}
