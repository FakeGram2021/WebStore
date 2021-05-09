package tim6.inventorymanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim6.inventorymanagement.models.entities.Article;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {}
