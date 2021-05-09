package tim6.inventorymanagement.web.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim6.inventorymanagement.models.entities.Article;
import tim6.inventorymanagement.services.ArticleService;
import tim6.inventorymanagement.web.v1.dto.article.ArticleConverter;
import tim6.inventorymanagement.web.v1.dto.article.ArticleGetDTO;
import tim6.inventorymanagement.web.v1.dto.article.ArticlePostDTO;
import tim6.inventorymanagement.web.v1.dto.article.ArticlePutDTO;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/articles")
@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(security = {@SecurityRequirement(name = "Bearer-token")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageImpl<ArticleGetDTO>> getArticles(Pageable pageable) {
        Page<Article> articlesPage = this.articleService.getAll(pageable);
        PageImpl<ArticleGetDTO> responseBody = ArticleConverter.convert(articlesPage, pageable);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "Bearer-token")})
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleGetDTO> getArticleById(@PathVariable UUID id) {
        Article article = this.articleService.getById(id);
        ArticleGetDTO responseBody = ArticleConverter.convert(article);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "Bearer-token")})
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createArticle(
            @Valid @RequestBody ArticlePostDTO articlePostDTO) {
        Article article = ArticleConverter.convert(articlePostDTO);
        this.articleService.create(article);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(security = {@SecurityRequirement(name = "Bearer-token")})
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateArticle(
            @PathVariable UUID id, @Valid @RequestBody ArticlePutDTO articlePutDTO) {
        Article article = ArticleConverter.convert(articlePutDTO);
        this.articleService.updateById(article, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "Bearer-token")})
    @DeleteMapping(value = "{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable UUID id) {
        this.articleService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
