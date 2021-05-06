package com.urlshortenerapi.service;

import com.urlshortenerapi.dto.UrlListResponse;
import com.urlshortenerapi.dto.UrlLongRequest;
import com.urlshortenerapi.entity.Url;
import com.urlshortenerapi.repository.UrlRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final BaseConversion conversion;

    public UrlService(UrlRepository urlRepository, BaseConversion baseConversion) {
        this.urlRepository = urlRepository;
        this.conversion = baseConversion;
    }

    public String convertToShortUrl(UrlLongRequest request) {
        var url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setCreatedDate(new Date());
        url.setHitCount(0);
        var entity = urlRepository.save(url);

        return conversion.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        var id = conversion.decode(shortUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));
        entity.setHitCount(entity.getHitCount() + 1);

        urlRepository.save(entity);

        return entity.getLongUrl();
    }

    public List<UrlListResponse> getUrlList()
    {
        List<Url> list = urlRepository.findAll();

        List<UrlListResponse> response = new ArrayList<UrlListResponse>();

        for(Url url : list)
        {
            UrlListResponse urlObj = new UrlListResponse();
            urlObj.setLongUrl(url.getLongUrl());
            urlObj.setHitCount(url.getHitCount());

            response.add(urlObj);
        }

        return response;
    }
}
