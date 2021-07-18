package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.domain.entity.Board;
import com.example.board.dto.BoardDto;
import com.example.board.dto.MemberDto;
import com.example.board.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(memberDto.toEntity()).getId();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberWrapper = memberRepository.findByusername(username);
        Member member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }

    @Transactional
    public MemberDto getPost(Long id) {
        Member member = memberRepository.findById(id).get();

        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .password(member.getPassword())
                .username(member.getUsername())
                .build();
        return memberDto;
    }

    @Transactional
    public void loadDeletePost(Long id) {
        memberRepository.deleteById(id);
    }
}
