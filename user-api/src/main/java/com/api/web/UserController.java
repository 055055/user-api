package com.api.web;

import com.api.service.UserService;
import com.api.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(value = "/v1/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll(){
        return userService.findAll();
    }

    @GetMapping(value = "/{seq}")
    public UserDto findBySeq(@PathVariable Long seq){
        return userService.findyBySeq(seq);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserSaveReqDto req){
        UserSaveResDto result = userService.save(req);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{seq}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long seq){
        userService.delete(seq);
    }

    @PatchMapping(value = "/{seq}")
    public UserUpdateResDto update(@RequestBody UserUpdateReqDto req, @PathVariable Long seq){
        return userService.update(seq,req);
    }



}
