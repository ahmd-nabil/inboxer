package nabil.inboxer.folders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Ahmed Nabil
 */

@Data
@Builder
@AllArgsConstructor
@Table("folders_by_user")
public class Folder {

    @PrimaryKeyColumn(name = "user_id", ordinal=0, type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "label", ordinal = 1, type = PrimaryKeyType.CLUSTERED) // used for uniqueness
    private String label;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String color;
}
