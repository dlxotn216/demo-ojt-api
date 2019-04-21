package ojt.crscube.base.interfaces.criteria;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by taesu on 2019-04-21.
 */
@Data
@Slf4j
public class SearchOption {
    private String key;
    private SearchOperation operation;
    private String value;

    public SearchOption(String key, SearchOperation operation, String value) {
        if (key.equalsIgnoreCase("updatedBy")) {
            key = "entityBase.updatedBy";
        } else if (key.equalsIgnoreCase("updateDateTime")) {
            key = "entityBase.updateDateTime";
        }
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}