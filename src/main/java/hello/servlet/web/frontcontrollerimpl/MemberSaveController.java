package hello.servlet.web.frontcontrollerimpl;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import java.util.Map;

public class MemberSaveController {
    MemberRepository memberRepository = MemberRepository.getInstance();

    protected String service(Model model, Map<String, String[]> paramMap){
        String username = paramMap.get("username")[0];
        int age = Integer.parseInt(paramMap.get("age")[0]);

        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model
        model.addAttribute("member", member);

        return "save-result";
    }
}
