package tim6.ordering.container;

public abstract class AbstractContainerBaseTest {

    static final TestPostgreSQLContainer container;

    static {
        container = new TestPostgreSQLContainer();
        container.start();
    }
}
