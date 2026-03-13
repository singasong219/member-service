package com.sparta.memberservice.repository;

import com.sparta.memberservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
