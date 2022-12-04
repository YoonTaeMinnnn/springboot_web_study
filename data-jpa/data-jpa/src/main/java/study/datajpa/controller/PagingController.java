package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class PagingController {

    private final MemberRepository memberRepository;

    @GetMapping("/page")
    public Page<MemberDto> paging(@PageableDefault(size = 3, sort = "userName", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> slice = memberRepository.findByAge(10, pageable);
        return slice.map(MemberDto::new);
    }
}
