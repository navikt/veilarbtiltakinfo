package no.nav.fo.veilarbtiltakinfo.config;

import no.nav.sbl.jdbc.DataSourceFactory;
import no.nav.sbl.jdbc.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static no.nav.sbl.util.EnvironmentUtils.getOptionalProperty;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    public static final String VEILARBTILTAKINFODB_URL = "VEILARBTILTAKINFODB_URL";
    public static final String VEILARBTILTAKINFODB_USERNAME = "VEILARBTILTAKINFODB_USERNAME";
    public static final String VEILARBTILTAKINFODB_PASSWORD = "VEILARBTILTAKINFODB_PASSWORD";

    @Bean
    public DataSource dataSource() {
        return DataSourceFactory.dataSource()
            .url(getRequiredProperty(VEILARBTILTAKINFODB_URL))
            .username(getRequiredProperty(VEILARBTILTAKINFODB_USERNAME))
            .password(getOptionalProperty(VEILARBTILTAKINFODB_PASSWORD).orElse(""))
            .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Database database(JdbcTemplate jdbcTemplate) {
        return new Database(jdbcTemplate);
    }

}
