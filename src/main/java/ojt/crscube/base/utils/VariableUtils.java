package ojt.crscube.base.utils;

/**
 * Created by taesu at : 2019-04-19
 * 
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class VariableUtils {
    private VariableUtils() {
    }
    
    public static final String EMPTY_STRING = "";

    public static void requireNonNull(Object object, String validationMessage){
        if(object == null){
            throw new IllegalArgumentException(validationMessage);
        }
    }
}
