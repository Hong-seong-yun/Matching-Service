package healthmatching.matchingservice.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mKey;
    @Column(nullable = false)
    private String mId;
    @Column(nullable = false)
    private String mName;
    @Column(nullable = false)
    private String mPhonenumber;
    @Column(nullable = false)
    private Integer mAge;
    @Column(nullable = false)
    private String mPw;
    @Column(nullable = false)
    private String mRole;
    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Trainer trainer;
    @OneToMany(mappedBy = "matchCustomer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Matching> matching;
    @OneToMany(mappedBy = "rCustomer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Register> registers;

    @Builder
    public Member(Long mKey, String mId, String mName, String mPhonenumber, Integer mAge, String mPw, String mRole) {
        this.mKey = mKey;
        this.mId = mId;
        this.mName = mName;
        this.mPhonenumber = mPhonenumber;
        this.mAge = mAge;
        this.mPw = mPw;
        this.mRole = mRole;
    }
    public void updateUser(String mId, String mName, String mPhonenumber, Integer mAge, String mPw, String mRole) {
        this.mId = mId;
        this.mName = mName;
        this.mPhonenumber = mPhonenumber;
        this.mAge = mAge;
        this.mPw = mPw;
        this.mRole = mRole;
    }

    // 유저의 권한 목록(어떤 권한을 가졌는지 return해준다.)??
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.mRole));
    }

    @Override
    public String getPassword() {
        return this.mPw;
    }

    @Override
    public String getUsername() {
        return this.mId;
    }

    /* 계정 만료 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /* 계정 잠김 여부
     *  true : 잠기지 않음
     *  false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /* 비밀번호 만료 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /* 사용자 활성화 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
