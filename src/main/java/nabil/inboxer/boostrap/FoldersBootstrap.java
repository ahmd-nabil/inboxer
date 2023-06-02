package nabil.inboxer.boostrap;

import lombok.RequiredArgsConstructor;
import nabil.inboxer.folders.Folder;
import nabil.inboxer.folders.FolderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Ahmed Nabil
 */
@Component
@RequiredArgsConstructor
public class FoldersBootstrap implements CommandLineRunner {
    private final FolderRepository folderRepository;
    @Override
    public void run(String... args) throws Exception {
        folderRepository.save(Folder.builder().userId("ahmd-nabil").label("inbox").color("blue").build());
        folderRepository.save(Folder.builder().userId("ahmd-nabil").label("important").color("red").build());
        folderRepository.save(Folder.builder().userId("ahmd-nabil").label("sent").color("gray").build());
    }
}
