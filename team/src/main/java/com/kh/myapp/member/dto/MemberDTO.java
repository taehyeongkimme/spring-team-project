package com.kh.myapp.member.dto;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.myapp.authority.dto.AuthorityDTO;

import lombok.Data;

@Data
@Entity
public class MemberDTO implements UserDetails{

	private static final long serialVersionUID = -7122374730281401947L;

	@Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message="ex)aaa@bbb.com")
	private String id;				// 아이디(이메일)
	@Size(min=4,max=30, message="비밀번호는 4~30자리로 입력바랍니다.")
	private String pw;				// 비밀번호
	private String name;			// 이름	
	@Size(min=4,max=10, message="닉네임은 3~10자리로 입력바랍니다.")
	private String nickname;	// 닉네임
	@Pattern(regexp="^(02|010)-\\d{3,4}-\\d{4}$",message="ex)010-1234-5678")
	private String phone;			// 휴대폰번호
	private String gender;		// 성별
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="생년월일 ex)xxxx-xx-xx")
	private String birth;			// 생년월일
	private String denied;		// 활동정지 ('Y','N')
	private String grade;			// 회원등급
	private Timestamp cdate;	// 생성일
	private Timestamp udate;	// 수정일
	
	private List<AuthorityDTO> authorities; 		//권한
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return pw;
	}
	@Override
	public String getUsername() {
		return id;
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
	
	public static MemberDTO current() {
		
		try {
			return (MemberDTO)SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
