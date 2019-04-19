package ojt.crscube.base.utils;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

/**
 * Created by taesu at : 2019-04-19
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class EncryptionUtils {
    private EncryptionUtils(){}
    
    public static String encode(String source){
        return createDelegatingPasswordEncoder().encode(source);
    }
    
    public static boolean matches(String source, String encoded){
        return createDelegatingPasswordEncoder().matches(source, encoded);
    }
}
