package tim6.reporting.adapter.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim6.reporting.domain.model.SoldQuantityArticleStatistics;
import tim6.reporting.domain.service.ReportService;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(path = "/mostSold")
    public ResponseEntity<?> mostSoldArticlesReport(
            @RequestParam @DateTimeFormat(pattern="ddMMyyyy")  Date from,
            @RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date to,
            @RequestParam(defaultValue = "5") long articlesLimit
    ) {
        return new ResponseEntity<>(reportService.generateMostSoldArticlesReport(from, to, articlesLimit), HttpStatus.OK);
    }

    @GetMapping(path = "/profit")
    public ResponseEntity<?> mostProfitableArticlesReport(
            @RequestParam @DateTimeFormat(pattern="ddMMyyyy")  Date from,
            @RequestParam @DateTimeFormat(pattern="ddMMyyyy") Date to,
            @RequestParam(defaultValue = "5") long articlesLimit
    ) {
        return new ResponseEntity<>(reportService.generateMostProfitableArticlesReport(from, to, articlesLimit), HttpStatus.OK);
    }
}
