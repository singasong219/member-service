package com.sparta.memberservice.controller;

import com.sparta.memberservice.MemberServiceApplication;
import com.sparta.memberservice.dto.MemberRequestDto;
import com.sparta.memberservice.dto.MemberResponseDto;
import com.sparta.memberservice.entity.Member;
import com.sparta.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> saveMember(@RequestBody MemberRequestDto dto) {
        log.info("[API - LOG] POST /api/members 요청");
        Member member = memberService.saveMember(dto);
        return ResponseEntity.ok(new MemberResponseDto(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long id) {
        log.info("[API - LOG] GET /api/members/{} 요청", id);
        Member member = memberService.getMember(id);
        return ResponseEntity.ok(new MemberResponseDto(member));

    }
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        log.info("[API - LOG] POST /api/members/{}/profile-image 요청", id);
        String url = memberService.uploadProfileImage(id, file);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{id}/profile-image")
    public ResponseEntity<String> getProfileImage(@PathVariable Long id) {
        log.info("[API - LOG] GET /api/members/{}/profile-image 요청", id);
        String presignedUrl = memberService.getProfileImage(id);
        return ResponseEntity.ok(presignedUrl);
    }
}
