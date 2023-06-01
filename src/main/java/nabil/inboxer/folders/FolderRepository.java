package nabil.inboxer.folders;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ahmed Nabil
 */

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String> {
    List<Folder> findAllByUserId(Integer id);
}
