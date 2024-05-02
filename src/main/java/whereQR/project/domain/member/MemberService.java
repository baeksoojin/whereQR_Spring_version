package whereQR.project.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whereQR.project.domain.member.dto.KakaoSignupDto;
import whereQR.project.exception.CustomExceptions.NotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Boolean existsMemberByKakaoIdAndRole( Long kakaoId, Role role ){
        return memberRepository.existsMemberByKakaoIdAndRole(kakaoId, role);
    }

    public Member getMemberById(UUID id){
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("해당하는 사용자가 존재하지 않습니다.", this.getClass().toString()));
    }

    public Member getMemberByKakaoIdAndRole(Long kakaoId, Role role){
        return memberRepository.findMemberByKakaoIdAndRole(kakaoId, role).orElseThrow(() -> new NotFoundException("해당하는 사용자가 존재하지 않습니다.", this.getClass().toString()));
    }

    public Member getMemberByRefreshToken(String refreshToken){
        return memberRepository.findMemberByRefreshToken(refreshToken).orElseThrow(() ->new NotFoundException("해당하는 사용자가 존재하지 않습니다.", this.getClass().toString()));
    }

    @Transactional
    public Member signUp(KakaoSignupDto signupDto, Role role){
        Member member = new Member(signupDto.getKakaoId(),signupDto.getUsername(),signupDto.getPhoneNumber(), role);
        memberRepository.save(member);
        return member;
    }

}