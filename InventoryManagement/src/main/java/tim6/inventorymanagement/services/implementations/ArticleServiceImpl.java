package tim6.inventorymanagement.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tim6.inventorymanagement.models.entities.Article;
import tim6.inventorymanagement.repositories.ArticleRepository;
import tim6.inventorymanagement.services.ArticleService;

import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> getAll(Pageable pageable) {
        return this.articleRepository.findAll(pageable);
    }

    @Override
    public Article getById(UUID id) {
        return this.articleRepository.getOne(id);
    }

    @Override
    public Article create(Article article) {
        return this.articleRepository.save(article);
    }

    @Override
    public Article updateById(Article article, UUID id) {
        Article storedArticle = this.getById(id);

        storedArticle.setName(article.getName());
        storedArticle.setDescription(article.getDescription());
        storedArticle.setPrice(article.getPrice());
        storedArticle.setAmountInStock(article.getAmountInStock());
        storedArticle.setImageUrl(article.getImageUrl());

        return this.articleRepository.save(storedArticle);
    }

    @Override
    public void deleteById(UUID id) {
        this.articleRepository.deleteById(id);
    }
}
