package com.subway.member;

import com.subway.member.dto.AuthMember;
import com.subway.member.dto.MemberSaveRequest;
import com.subway.member.resolver.AuthMemberPrincipal;
import com.subway.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody MemberSaveRequest request) {
        Long id = memberService.save(request);
        return ResponseEntity.created(URI.create(String.format("/members/%d", id))).body(id);
    }

    @GetMapping("me")
    public AuthMember findMyInfo(@AuthMemberPrincipal AuthMember authMember) {
        return authMember;
    }
}
