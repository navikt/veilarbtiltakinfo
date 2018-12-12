package no.nav.fo.veilarbtiltakinfo;

import lombok.SneakyThrows;
import no.nav.fo.veilarbtiltakinfo.config.DataSourceConfig;
import no.nav.fo.veilarbtiltakinfo.config.MigrationUtils;
import org.h2.engine.Database;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DatabaseTest {

    private static AnnotationConfigApplicationContext annotationConfigApplicationContext;
    private static PlatformTransactionManager platformTransactionManager;
    private TransactionStatus transactionStatus;

    @SneakyThrows
    protected static void setupContext(Class<?>... classes) {
        DatabaseTestContext.setInMemoryDataSourceProperties();

        List<Class> springConfigurationClasses = new ArrayList<>(Arrays.<Class>asList(DataSourceConfig.class));
        springConfigurationClasses.addAll(Arrays.<Class>asList(classes));
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext(springConfigurationClasses.toArray(new Class[0]));
        annotationConfigApplicationContext.start();
        platformTransactionManager = getBean(PlatformTransactionManager.class);

        MigrationUtils.createTables(getBean(DataSource.class));
    }

    @BeforeEach
    @Before
    public void injectAvhengigheter() {
        annotationConfigApplicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @BeforeEach
    @Before
    public void startTransaksjon() {
        transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    @After
    public void rollbackTransaksjon() {
        if (platformTransactionManager != null && transactionStatus != null) {
            platformTransactionManager.rollback(transactionStatus);
        }
    }

    protected static <T> T getBean(Class<T> requiredType) {
        return annotationConfigApplicationContext.getBean(requiredType);
    }

    @AfterAll
    @AfterClass
    public static void close() {
        if (annotationConfigApplicationContext != null) {
            annotationConfigApplicationContext.stop();
            annotationConfigApplicationContext.close();
            annotationConfigApplicationContext = null;
        }
    }

}
