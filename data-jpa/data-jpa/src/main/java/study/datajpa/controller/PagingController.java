package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class PagingController {

    private final MemberRepository memberRepository;

    @GetMapping("/page")
    public void paging(@PageableDefault(size = 3, sort = "userName", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> page = memberRepository.findByAge(10, pageable);
        System.out.println("page.getContent() = " + page.getContent());
        System.out.println("page.getTotalElements() = " + page.getTotalElements());
    }
}
