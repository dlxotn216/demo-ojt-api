package ojt.crscube.base.interfaces.criteria;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.endsWithIgnoreCase;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by taesu on 2019-04-21.
 */
@Data
@Slf4j
public class SearchCriteria {
    private List<SearchOption> options;
    private String condition;


    public SearchCriteria(String searchOptionString, String condition) {
        this.options = new ArrayList<>();
        this.condition = condition;
        if (isEmpty(searchOptionString)) {
            return;
        }

        Pattern pattern = Pattern.compile("(.+?)(>=|<=|!=|=|:|<|>)(.*?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchOptionString + ",");
        while (matcher.find()) {
            try {
                options.add(new SearchOption(matcher.group(1),
                        SearchOperation.fromString(matcher.group(2)),
                        matcher.group(3)));
            } catch (UnsupportedOperationException e) {
                //Ignore...
                log.warn("{} 에러로 인해 특정 criteria가 무시됩니다", e.getMessage(), e);
            }
        }
    }

    public Boolean isAndCondition(){
        return endsWithIgnoreCase(this.condition, "and");
    }

    public Boolean isOrCondition(){
        return endsWithIgnoreCase(this.condition, "or");
    }
}
