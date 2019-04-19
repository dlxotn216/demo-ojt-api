package ojt.crscube.auth;

import ojt.crscube.member.domain.model.Member;

/**
 * Created by taesu on 2019-04-19.
 */
public final class AuthenticationContextHolder {
    private static ThreadLocal<Member> memberThreadLocal = new ThreadLocal<>();

    private AuthenticationContextHolder() {
    }

    public static void setAuthentication(Member member) {
        memberThreadLocal.set(member);
    }

    public static Member getAuthentication() {
        return memberThreadLocal.get();
    }

    public static void remove() {
        memberThreadLocal.remove();
    }

}
