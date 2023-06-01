package nabil.inboxer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Ahmed Nabil
 */
@Configuration
public class DatastaxConfig {
    @Value("${datastax.astra.secure-connect-bundle}")
    private File dataStaxSecureConnectBundle;

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
        Path bundle = dataStaxSecureConnectBundle.toPath();
        return cqlBuilder -> cqlBuilder.withCloudSecureConnectBundle(bundle);
    }
}
