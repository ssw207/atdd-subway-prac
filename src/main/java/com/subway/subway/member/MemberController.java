package com.subway.subway.member;

import com.subway.subway.member.dto.MemberSaveRequest;
import com.subway.subway.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
