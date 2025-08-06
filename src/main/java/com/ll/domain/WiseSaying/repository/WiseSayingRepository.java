package com.ll.domain.WiseSaying.repository;

import com.ll.domain.WiseSaying.entity.WiseSaying;
import com.ll.standard.dto.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying save(WiseSaying wiseSaying) {
        if (wiseSaying.isNew()) {
            wiseSaying.setId(++lastId);
            wiseSayings.add(wiseSaying);
        }
        return wiseSaying;
    }

    public List<WiseSaying> findForList(Pageable pageable) {
        return wiseSayings
                .reversed()
                .stream()
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    public List<WiseSaying> findForListByContent(String content, Pageable pageable) {
        return wiseSayings
                .reversed()
                .stream()
                .filter(w -> w.getContent().contains(content))
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());}

    public List<WiseSaying> findForListByAuthor(String author, Pageable pageable) {
        return wiseSayings
                .reversed()
                .stream()
                .filter(w -> w.getAuthor().contains(author))
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());}

    public List<WiseSaying> findForListByContentOrAuthor(String keyword, Pageable pageable) {
        return wiseSayings
                .reversed()
                .stream()
                .filter(w -> w.getAuthor().contains(keyword) || w.getContent().contains(keyword))
                .skip(pageable.getSkipCount())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    public WiseSaying findById(int id) {
        int index = findIndexById(id);
        if (index == -1) {
            return null;
        }
        return wiseSayings.get(index);
    }

    private int findIndexById(int id) {
        return IntStream
                .range(0, wiseSayings.size())
                .filter(i -> wiseSayings.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
    }

    public void delete(WiseSaying wiseSaying) {
        wiseSayings.remove(wiseSaying);
    }
}
