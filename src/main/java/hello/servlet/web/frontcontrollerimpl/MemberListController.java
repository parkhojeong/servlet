package hello.servlet.web.frontcontrollerimpl;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import java.util.List;

public class MemberListController {
    MemberRepository memberRepository = MemberRepository.getInstance();

    protected String service(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "members";
    }
}
