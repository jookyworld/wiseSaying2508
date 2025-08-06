package com.ll.domain.WiseSaying.service;

import com.ll.AppContext;
import com.ll.domain.WiseSaying.entity.WiseSaying;
import com.ll.domain.WiseSaying.repository.WiseSayingRepository;
import com.ll.standard.dto.Pageable;

import java.util.List;

public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = AppContext.wiseSayingRepository;
    }

    public WiseSaying write(String author, String content) {
        WiseSaying wiseSaying = new WiseSaying(author, content);
        return wiseSayingRepository.save(wiseSaying);
    }

    public List<WiseSaying> findForList(String keywordType, String keyword, Pageable pageable) {
        if (keyword.isBlank()) {
            return wiseSayingRepository.findForList(pageable);
        }
        return switch (keywordType) {
            case "content" -> wiseSayingRepository.findForListByContent(keyword, pageable);
            case "author" -> wiseSayingRepository.findForListByAuthor(keyword, pageable);
            default -> wiseSayingRepository.findForListByContentOrAuthor(keyword, pageable);
        };
    }

    public boolean delete(int id) {
        WiseSaying wiseSaying = wiseSayingRepository.findById(id);

        if (wiseSaying == null) {
            return false;
        }

        wiseSayingRepository.delete(wiseSaying);

        return true;
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void modify(WiseSaying wiseSaying, String author, String content) {
        wiseSaying.setAuthor(author);
        wiseSaying.setContent(content);
        wiseSayingRepository.save(wiseSaying);
    }
}
