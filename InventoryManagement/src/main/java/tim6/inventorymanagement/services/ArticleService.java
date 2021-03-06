package tim6.inventorymanagement.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tim6.inventorymanagement.models.entities.Article;

import java.util.UUID;

public interface ArticleService {

    Page<Article> getAll(Pageable pageable);

    Article getById(UUID id);

    Article create(Article article);

    Article updateById(Article article, UUID id);

    void deleteById(UUID id);
}
