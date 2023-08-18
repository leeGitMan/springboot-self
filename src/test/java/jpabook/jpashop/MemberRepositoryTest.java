package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired MemberRepository mr;

    @Test
    @Transactional // Transactional이 Test에 있으면 Test 완료 후 DB를 ROLLBACK 하기에 DB에 데이터가 안 남는다.
    @Rollback(false) // 를 해주면 DB에 rollback이 안되고 데이터가 남아았는 걸 볼 수 있다.
    // Rollback을 false로 안해줄 경우 insert구문이 로거에 안 찍힐 수 있음 아무리 ps6py 라이브러리와 야믈에 org.hibernate.type: trace를 해도
    // 그러니 false로 하고 sql문이 찍히는 걸 보자
    // https://github.com/gavlyukovskiy/spring-boot-data-source-decorator
    public void testMember() throws Exception{

        // given
        Member member = new Member();
        member.setUserName("memberA");

        // when
        long saveId = mr.save(member);
        Member findMember = mr.find(saveId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(findMember).isEqualTo(member);

        // 같은 트랜잭션 안에서 저장되기에 같은 영속성이 된다.
        // 같은 영속성 컨텍스트 안에서 id값이 같으면 당연히 true
        // 즉 같은 entity
        System.out.println("findMember == member " + (findMember == member));
    }

    
}