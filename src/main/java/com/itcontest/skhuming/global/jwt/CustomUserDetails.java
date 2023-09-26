package com.itcontest.skhuming.global.jwt;

/*
Spring Security는 유저 인증과정에서 UserDetails를 참조하여 인증을 진행한다.
UserDetails를 아래와 같이 상속하여 DB에 위에서 선언한 사용자의 정보를 토대로 인증을 진행하도록 설정한다.

Member에 바로 UserDetails를 상속해도 동작은 하겠지만 그렇게하면 엔티티가 오염되어 향후 Member 엔티티를 사용하기 어려워지기 때문에 CustomUsetDetails를 따로 만들어줬다.

JWT를 이용할 것이기 때문에 아래 isAccountNonExpired() 아래로 4개속성은 true로 설정한다.
 */

import com.itcontest.skhuming.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public final Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream().map(o -> new SimpleGrantedAuthority(
                o.getName()
        )).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getPwd();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
