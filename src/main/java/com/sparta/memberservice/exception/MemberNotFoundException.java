package com.sparta.memberservice.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id){
        super("존재하지 않는 멤버입니다. id: " + id);
    }
}
