package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
/*
이렇게 어노테이션을 달아주면 기본적으로 public 메서드에는 readOnly=true가 적용
그러나 회원 가입 메서드와 같이 public 메서드에 따로 Transactional을 달아주면 디폴트가 readOnly=false이기에 false가 적용된다.
읽기 전용에는 readOnly = true를 달아주는 것이 좋다.
* */
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    * 회원 가입
    * */
    @Transactional
    public Long join(Member member) {
        validateDuplicate(member);
        memberRepository.save(member);
        return member.getId();
    }


    /*
    * 중복회원 검증
    * */
    private void validateDuplicate(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("중복된 회원입니다.");
        }
    }

    /*
     * 전체 회원 조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findALl();
    }

    /*
    * 회원 단 건 조회
    * */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
