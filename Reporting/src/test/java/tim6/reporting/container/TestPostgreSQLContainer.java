package tim6.reporting.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestPostgreSQLContainer extends PostgreSQLContainer<TestPostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:13";

    public TestPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", this.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", this.getUsername());
        System.setProperty("TEST_DB_PASSWORD", this.getPassword());
    }
}
