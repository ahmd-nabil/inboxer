package nabil.inboxer.services;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.RequiredArgsConstructor;
import nabil.inboxer.email_list.EmailListItem;
import nabil.inboxer.email_list.EmailListItemRepository;
import nabil.inboxer.folders.Folder;
import nabil.inboxer.folders.FolderRepository;
import nabil.inboxer.mappers.RecipientsMapper;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Ahmed Nabil
 */
@Service
@RequiredArgsConstructor
public class MainService {

    private final FolderRepository folderRepository;
    private final EmailListItemRepository emailListItemRepository;
    private final RecipientsMapper recipientsMapper;
    public void addEmailListToModel(Model model, String userId, String label) {
        List<EmailListItem> emailListItems = emailListItemRepository.findAllByKey_UserIdAndKey_Label(userId, label);
        emailListItems.forEach(item -> {
            UUID timeUUID = item.getKey().getCreatedAt();
            Date createdDate = new Date(Uuids.unixTimestamp(timeUUID));
            item.setTimeAgo(new PrettyTime().format(createdDate));
            item.setToAsString(recipientsMapper.convertListToString(item.getTo()));
        });
        model.addAttribute("emailItems", emailListItems);
    }

    public void addUserFoldersToModel(Model model, String userId) {
        List<Folder> userFolders = folderRepository.findAllByUserId(userId);
        model.addAttribute("userFolders", userFolders);
    }

    public void addUserNameToModel(Model model, String userName) {
        model.addAttribute("userName", userName);
    }

}
