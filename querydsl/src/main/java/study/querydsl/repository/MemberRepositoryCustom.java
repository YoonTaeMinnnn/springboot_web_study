package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchDto;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchDto memberSearchDto);

    Page<MemberTeamDto> searchPageSimple(MemberSearchDto memberSearchDto, Pageable pageable);

    Page<MemberTeamDto> searchPageComplex(MemberSearchDto memberSearchDto, Pageable pageable);
}
