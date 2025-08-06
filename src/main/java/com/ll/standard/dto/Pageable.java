package com.ll.standard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Pageable {
    private final int pageNo;
    private final int pageSize;

    public long getSkipCount() {
        return (long) (pageNo - 1) * pageSize;
    }
}
