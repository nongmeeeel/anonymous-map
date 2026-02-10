package com.yongman.common.controller;

import com.yongman.post.entity.Post;
import com.yongman.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SitemapController {

    private final PostRepository postRepository;
    private static final String SITE_URL = "https://anonymousmap.com";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String sitemap() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // 정적 페이지
        addUrl(xml, "/", "daily", "1.0");
        addUrl(xml, "/about", "monthly", "0.8");
        addUrl(xml, "/privacy", "monthly", "0.5");
        addUrl(xml, "/terms", "monthly", "0.5");
        addUrl(xml, "/contact", "monthly", "0.6");

        // 동적 게시글 페이지
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            String lastmod = post.getRegDt().format(DATE_FORMAT);
            addUrl(xml, "/post/" + post.getId(), "weekly", "0.7", lastmod);
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    private void addUrl(StringBuilder xml, String path, String changefreq, String priority) {
        addUrl(xml, path, changefreq, priority, null);
    }

    private void addUrl(StringBuilder xml, String path, String changefreq, String priority, String lastmod) {
        xml.append("  <url>\n");
        xml.append("    <loc>").append(SITE_URL).append(path).append("</loc>\n");
        if (lastmod != null) {
            xml.append("    <lastmod>").append(lastmod).append("</lastmod>\n");
        }
        xml.append("    <changefreq>").append(changefreq).append("</changefreq>\n");
        xml.append("    <priority>").append(priority).append("</priority>\n");
        xml.append("  </url>\n");
    }
}
