package tim6.inventorymanagement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import tim6.inventorymanagement.article.integration.*;

@RunWith(Suite.class)
@SuiteClasses({
    GetAllArticlesTest.class,
    GetArticleByIdTest.class,
    CreateArticleTest.class,
    UpdateArticleTest.class,
    DeleteArticleTest.class
})
public class InventoryManagementApplicationTests {}
