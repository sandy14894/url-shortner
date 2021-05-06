package com.urlshortenerapi.controller;

import com.urlshortenerapi.dto.UrlListResponse;
import com.urlshortenerapi.dto.UrlLongRequest;
import com.urlshortenerapi.service.UrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @CrossOrigin
    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping(path = "/shortenUrl")
    public List<String> convertToShortUrl(@RequestBody UrlLongRequest request) {
        List<String> response = new ArrayList<>();
         response.add(urlService.convertToShortUrl(request));
         return response;
    }

    @CrossOrigin
    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        var url = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080/index.html"))
                .build();
    }

    @CrossOrigin
    @ApiOperation(value = "getUrlList", notes = "Finds all urls and hit count for them")
    @GetMapping(path = "/getUrlList")
    public List<UrlListResponse> getUrlList() {
        return urlService.getUrlList();

    }
}
