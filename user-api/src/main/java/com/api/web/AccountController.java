package com.api.web;

import com.api.common.config.JwtTokenProcess;
import com.api.service.AccountService;
import com.api.web.dto.AccountDto;
import com.api.web.dto.AccountLoginReqDto;
import com.api.web.dto.AccountLoginResDto;
import com.api.web.dto.AccountSaveReqDto;
import com.api.web.dto.AccountSaveResDto;
import com.api.web.dto.AccountUpdateReqDto;
import com.api.web.dto.AccountUpdateResDto;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/v1/user")
@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
	private final JwtTokenProcess jwtTokenProcess;

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String login(@RequestBody AccountLoginReqDto req) {
		AccountLoginResDto userDto = accountService.login(req);
		return jwtTokenProcess.createToken(userDto.getEmail(), userDto.getRole());
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
	public ResponseEntity<?> save(@RequestBody @Valid AccountSaveReqDto req) {
		AccountSaveResDto result = accountService.save(req);
		return new ResponseEntity(result, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{seq}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long seq) {
		accountService.delete(seq);
	}

	@PutMapping(value = "/{seq}")
	public AccountUpdateResDto update(@RequestBody @Valid AccountUpdateReqDto req,
		@PathVariable Long seq) {
		return accountService.update(seq, req);
	}
}
