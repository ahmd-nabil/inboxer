package nabil.inboxer.mappers;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Component
public class RecipientsMapper {

    public String convertListToString(List<String>recipientsIds) {
        return Strings.join(recipientsIds, ',');
    }

    public List<String> convertStringToList(String recipientsIds) {
        return Arrays.stream(recipientsIds.split(",")).map(String::strip).distinct().toList();
    }

}
