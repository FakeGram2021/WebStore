package tim6.inventorymanagement.web.v1.dto.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tim6.inventorymanagement.models.entities.Article;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleConverter {

    public static PageImpl<ArticleGetDTO> convert(Page<Article> articles, Pageable pageable) {
        List<ArticleGetDTO> content = ArticleConverter.convert(articles.getContent());
        return new PageImpl<>(content, pageable, articles.getTotalPages());
    }

    public static List<ArticleGetDTO> convert(Collection<Article> articles) {
        return articles.stream().map(ArticleConverter::convert).collect(Collectors.toList());
    }

    public static ArticleGetDTO convert(Article article) {
        return new ArticleGetDTO(
                article.getId(),
                article.getName(),
                article.getDescription(),
                article.getPrice(),
                article.getAmountInStock(),
                article.getImageUrl(),
                article.getVersion());
    }

    public static Article convert(ArticlePostDTO article) {
        return new Article(
                article.getName(),
                article.getDescription(),
                article.getPrice(),
                article.getAmountInStock(),
                article.getImageUrl());
    }

    public static Article convert(ArticlePutDTO article) {
        return new Article(
                article.getName(),
                article.getDescription(),
                article.getPrice(),
                article.getAmountInStock(),
                article.getImageUrl(),
                article.getVersion());
    }
}
