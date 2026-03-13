package com.sparta.memberservice.service;

import com.sparta.memberservice.dto.MemberRequestDto;
import com.sparta.memberservice.entity.Member;
import com.sparta.memberservice.exception.MemberNotFoundException;
import com.sparta.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

        private final S3Service s3Service;

    public Member saveMember(MemberRequestDto dto) {
        log.info("[API - LOG] 팀원 저장 요청 - name: {}, age: {}, mbti: {}", dto.getName(), dto.getAge(), dto.getMbti());
        Member member = new Member (dto.getName(), dto.getAge(), dto.getMbti());
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        log.info("[API - LOG] 팀원 조회 요청 - id: {}", id);
        return memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[API - LOG] 팀원 조회 실패 - id: {}", id, new MemberNotFoundException(id));
                    return new MemberNotFoundException(id);
                });
    }
    public String uploadProfileImage(Long id, MultipartFile file) throws IOException {
        Member member = getMember(id);
        String key = s3Service.uploadFile(file);
        member.updateProfileImageUrl(key);
        memberRepository.save(member);
        return key;
    }

    public String getProfileImage(Long id) {
        Member member = getMember(id);
        String key = member.getProfileImageUrl();
        return s3Service.getPresignedUrl(key);
    }
}
