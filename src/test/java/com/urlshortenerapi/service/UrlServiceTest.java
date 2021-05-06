package com.urlshortenerapi.service;

import com.urlshortenerapi.dto.UrlListResponse;
import com.urlshortenerapi.dto.UrlLongRequest;
import com.urlshortenerapi.entity.Url;
import com.urlshortenerapi.repository.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    BaseConversion mockBaseConversion;

    @Mock
    List<Url> list;

    @InjectMocks
    UrlService urlService;

    @Test
    public void convertToShortUrlTest() {
        var url = new Url();
        url.setLongUrl("https://google.com");
        url.setCreatedDate(new Date());
        url.setHitCount(2);
        url.setId(5);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        when(mockBaseConversion.encode(url.getId())).thenReturn("f");

        var urlRequest = new UrlLongRequest();
        urlRequest.setLongUrl("https://google.com");

        assertEquals("f", urlService.convertToShortUrl(urlRequest));
    }

    @Test
    public void getOriginalUrlTest() {
        when(mockBaseConversion.decode("h")).thenReturn((long) 7);

        var url = new Url();
        url.setLongUrl("https://google.com");
        url.setCreatedDate(new Date());
        url.setHitCount(1);
        url.setId(7);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://google.com", urlService.getOriginalUrl("h"));

    }

    @Test
    public void getUrlListTest() {
        List<Url> list1 = new ArrayList<>();
        var url = new Url();
        url.setLongUrl("https://google.com");
        url.setCreatedDate(new Date());
        url.setHitCount(1);
        url.setId(7);

        list1.add(url);

        doReturn(list1).when(mockUrlRepository).findAll();

        assertEquals(1, urlService.getUrlList().size());

    }
}
