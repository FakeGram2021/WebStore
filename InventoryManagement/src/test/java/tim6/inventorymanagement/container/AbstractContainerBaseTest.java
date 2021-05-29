package tim6.inventorymanagement.container;

public abstract class AbstractContainerBaseTest {

    static final TestPostgreSQLContainer container;

    static {
        container = new TestPostgreSQLContainer();
        container.start();
    }
}
