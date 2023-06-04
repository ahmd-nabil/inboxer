package nabil.inboxer.mappers;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Component
public class RecipientsMapper {

    public String convertListToString(List<String>recipientsIds) {
        return String.join(", ", recipientsIds);
    }

    public List<String> convertStringToDistinctList(String recipientsIds) {
        return Arrays.stream(recipientsIds.split(",")).map(String::strip).filter(StringUtils::hasText).distinct().toList();
    }

}
