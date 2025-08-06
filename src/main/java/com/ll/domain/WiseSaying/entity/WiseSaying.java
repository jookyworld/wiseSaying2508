package com.ll.domain.WiseSaying.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WiseSaying {
    private int id;
    private String author;
    private String content;

    public WiseSaying(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public boolean isNew() {
        return id == 0;
    }
}
