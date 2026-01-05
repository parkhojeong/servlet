package hello.servlet.domain.member;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * not consider concurrency problem. consider Concurrent HashMap, AtomicLong
 */
public class MemberRepository {

    private Map<Long, Member> members = new HashMap<>();
    private static long sequence = 0L;

    @Getter
    private static final MemberRepository instance = new MemberRepository();

    private MemberRepository() {}

    public Member save(Member member) {
        member.setId(sequence++);
        members.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return members.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(members.values());
    }

    public void clearStore() {
        members.clear();
    }
}
