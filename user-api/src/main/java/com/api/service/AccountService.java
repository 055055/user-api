package com.api.service;

import com.api.entitiy.user.Account;
import com.api.error.AccountException;
import com.api.error.ErrorCode;
import com.api.repository.AccountRepository;
import com.api.web.dto.AccountDto;
import com.api.web.dto.AccountLoginReqDto;
import com.api.web.dto.AccountLoginResDto;
import com.api.web.dto.AccountSaveReqDto;
import com.api.web.dto.AccountSaveResDto;
import com.api.web.dto.AccountUpdateReqDto;
import com.api.web.dto.AccountUpdateResDto;
import com.api.web.dto.UserAccount;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

	private final AccountRepository accountRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<AccountDto> findAll() {
		List<Account> all = accountRepository.findAll();
		return all.stream().map(e -> modelMapper.map(e, AccountDto.class))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AccountDto findyBySeq(Long seq) {
		return accountRepository.findById(seq).map(e -> modelMapper.map(e, AccountDto.class))
			.orElseThrow(() -> {
				throw new AccountException(ErrorCode.ACCOUNT_NOT_FOUND);
			});
	}

	@Transactional
	public AccountSaveResDto save(AccountSaveReqDto req) {

		accountRepository.findByEmail(req.getEmail()).ifPresent(e -> {
			throw new AccountException(ErrorCode.ACCOUNT_DUPLICATE);
		});
		Account save = accountRepository.save(req.createAccount(passwordEncoder));
		return modelMapper.map(save, AccountSaveResDto.class);
	}

	@Transactional
	public void delete(Long seq) {
		accountRepository.findById(seq).ifPresentOrElse(e -> accountRepository.delete(e)
			, () -> {
				throw new AccountException(ErrorCode.ACCOUNT_NOT_FOUND);
			});
	}

	@Transactional
	public AccountUpdateResDto update(Long seq, AccountUpdateReqDto req) {
		Account account = accountRepository.findById(seq).orElseThrow(
			() -> {
				throw new AccountException(ErrorCode.ACCOUNT_NOT_FOUND);
			});
		account.updateUser(req.getPassword(), req.getName(), req.getRole());

		return modelMapper.map(account, AccountUpdateResDto.class);
	}

	@Transactional(readOnly = true)
	public AccountLoginResDto login(AccountLoginReqDto req) {
		Account account = accountRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_ID_OR_PASSWORD_INVALID));
		log.debug("account = " + account.toString());
		if (!passwordEncoder.matches(req.getPassword(), account.getPassword())) {
			throw new AccountException(ErrorCode.ACCOUNT_ID_OR_PASSWORD_INVALID);
		}
		return modelMapper.map(account, AccountLoginResDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(email));
		return new UserAccount(account);
	}
}
