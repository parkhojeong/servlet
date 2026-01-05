package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member("member1", 10);

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMembers = memberRepository.findAll();

        Assertions.assertThat(findMembers).contains(member1, member2);
    }

}