package com.api.service;

import com.api.entitiy.user.User;
import com.api.error.ServiceError;
import com.api.error.ServiceException;
import com.api.repository.UserRepository;
import com.api.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() throws ServiceException{
        try {
            List<User> all = userRepository.findAll();
            return all.stream().map(e -> modelMapper.map(e, UserDto.class)).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Exception : {}",e);
            throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR,e);
        }
    }

    @Transactional(readOnly = true)
    public UserDto findyBySeq(Long seq) throws ServiceException {
        try {
            return userRepository.findById(seq).map(e-> modelMapper.map(e,UserDto.class))
                    .orElseThrow(() -> { throw new ServiceException(ServiceError.USER_NOT_FOUND);});
        }catch (ServiceException se){
            log.error("ServiceException : {}",se.getServiceError(),se);
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.error("Exception : {}",e);
            throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR,e);
        }
    }

    @Transactional
    public UserSaveResDto save(UserSaveReqDto req) throws ServiceException{
        try {
            userRepository.findByEmail(req.getEmail()).ifPresent(e -> { throw new ServiceException(ServiceError.USER_ID_DUPLICATE);});
            User save = userRepository.save(req.createUser());
            return modelMapper.map(save,UserSaveResDto.class);

        }catch (ServiceException se){
            log.error("ServiceException : {}",se.getServiceError(),se);
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.error("Exception : {}",e);
            throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR,e);
        }
    }

    @Transactional
    public void delete(Long seq) {
        try {
             userRepository.findById(seq).ifPresentOrElse(e-> userRepository.delete(e)
                     ,() -> { throw new ServiceException(ServiceError.USER_NOT_FOUND);});
        }catch (ServiceException se){
            log.error("ServiceException : {}",se.getServiceError(),se);
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.error("Exception : {}",e);
            throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR,e);
        }
    }

    @Transactional
    public UserUpdateResDto update(Long seq, UserUpdateReqDto req) {
        try {
            User user = userRepository.findById(seq).orElseThrow(
                    () -> {
                        throw new ServiceException(ServiceError.USER_NOT_FOUND);
                    });
            user.updateUser(req.getPassword(), req.getName(), req.getRole());

            return modelMapper.map(user, UserUpdateResDto.class);
        }catch (ServiceException se){
            log.error("ServiceException : {}",se.getServiceError(),se);
            throw new ServiceException(se.getServiceError());
        }catch (Exception e){
            log.error("Exception : {}",e);
            throw new ServiceException(ServiceError.INTERNAL_SERIVCE_ERROR,e);
        }

    }
}
