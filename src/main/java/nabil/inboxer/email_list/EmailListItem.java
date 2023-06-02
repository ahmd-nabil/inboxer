package nabil.inboxer.email_list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("emails_by_user_folder")
public class EmailListItem {
    @PrimaryKey
    private EmailListItemKey key;
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> to;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String subject;

    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private boolean isRead = false;

    @Transient
    private String timeAgo;
}
