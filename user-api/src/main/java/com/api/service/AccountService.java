package com.api.service;

import com.api.entitiy.user.Account;
import com.api.error.ServiceError;
import com.api.error.ServiceException;
import com.api.repository.AccountRepository;
import com.api.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

	private final AccountRepository accountRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<AccountDto> findAll() throws ServiceException {
		try {
			List<Account> all = accountRepository.findAll();
			return all.stream().map(e -> modelMapper.map(e, AccountDto.class))
				.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR, e);
		}
	}

	@SuppressWarnings("checkstyle:FileTabCharacter")
	@Transactional(readOnly = true)
	public AccountDto findyBySeq(Long seq) throws ServiceException {
		try {
			return accountRepository.findById(seq).map(e -> modelMapper.map(e, AccountDto.class))
				.orElseThrow(() -> {
					throw new ServiceException(ServiceError.USER_NOT_FOUND);
				});
		} catch (ServiceException se) {
			log.error("ServiceException : {}", se.getServiceError(), se);
			throw new ServiceException(se.getServiceError());
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR, e);
		}
	}

	@Transactional
	public AccountSaveResDto save(AccountSaveReqDto req) throws ServiceException {
		try {
			accountRepository.findByEmail(req.getEmail()).ifPresent(e -> {
				throw new ServiceException(ServiceError.USER_ID_DUPLICATE);
			});
			Account save = accountRepository.save(req.createAccount(passwordEncoder));
			return modelMapper.map(save, AccountSaveResDto.class);

		} catch (ServiceException se) {
			log.error("ServiceException : {}", se.getServiceError(), se);
			throw new ServiceException(se.getServiceError());
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR, e);
		}
	}

	@Transactional
	public void delete(Long seq) {
		try {
			accountRepository.findById(seq).ifPresentOrElse(e -> accountRepository.delete(e)
				, () -> {
					throw new ServiceException(ServiceError.USER_NOT_FOUND);
				});
		} catch (ServiceException se) {
			log.error("ServiceException : {}", se.getServiceError(), se);
			throw new ServiceException(se.getServiceError());
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR, e);
		}
	}

	@Transactional
	public AccountUpdateResDto update(Long seq, AccountUpdateReqDto req) {
		try {
			Account account = accountRepository.findById(seq).orElseThrow(
				() -> {
					throw new ServiceException(ServiceError.USER_NOT_FOUND);
				});
			account.updateUser(req.getPassword(), req.getName(), req.getRole());

			return modelMapper.map(account, AccountUpdateResDto.class);
		} catch (ServiceException se) {
			log.error("ServiceException : {}", se.getServiceError(), se);
			throw new ServiceException(se.getServiceError());
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR, e);
		}

	}

	@Transactional(readOnly = true)
	public AccountLoginResDto login(AccountLoginReqDto req) throws ServiceException {
		Account account = accountRepository.findByEmail(req.getEmail())
			.orElseThrow(() -> new ServiceException(ServiceError.USER_OR_PASSWORD_INVALID));
		log.debug("account = " + account.toString());
		if (!passwordEncoder.matches(req.getPassword(), account.getPassword())) {
			throw new ServiceException(ServiceError.USER_OR_PASSWORD_INVALID);
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
