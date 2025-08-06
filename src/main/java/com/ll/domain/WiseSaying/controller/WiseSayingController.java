package com.ll.domain.WiseSaying.controller;

import com.ll.AppContext;
import com.ll.domain.WiseSaying.entity.WiseSaying;
import com.ll.domain.WiseSaying.service.WiseSayingService;
import com.ll.global.rq.Rq;
import com.ll.standard.dto.Pageable;

import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController() {
        this.scanner = AppContext.scanner;
        wiseSayingService = AppContext.wiseSayingService;
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = wiseSayingService.write(author, content);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void actionList(Rq rq) {
        String keywordType = rq.getParam("keywordType", "all");
        String keyword = rq.getParam("keyword", "");

//        if (!keywordType.isBlank() || !keyword.isBlank()) {
//            System.out.println("=========================");
//            System.out.println("검색타입 : " + keywordType);
//            System.out.println("검색어 : " + keyword);
//            System.out.println("=========================");
//        }

        int pageSize = rq.getParamsAsInt("pageSize", 5);
        int pageNo = rq.getParamsAsInt("page", 1);
        Pageable pageable = new Pageable(pageNo, pageSize);

        System.out.println("번호 / 작가 / 명언");
        System.out.println("=========================");


        for (WiseSaying wiseSaying : wiseSayingService.findForList(keywordType, keyword, pageable)) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / "
                    + wiseSaying.getContent());
        }
    }

    public void actionDelete(Rq rq) {
        int deleteId = rq.getParamsAsInt("id", -1);
        if (deleteId == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        boolean isDeleted = wiseSayingService.delete(deleteId);
        if (!isDeleted) {
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
        }
        System.out.println(deleteId + "번 명언이 삭제되었습니다.");
    }

    public void actionModify(Rq rq) {
        int modifyId = rq.getParamsAsInt("id", -1);
        if (modifyId == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }
        WiseSaying wiseSaying = wiseSayingService.findById(modifyId);
        if (wiseSaying == null) {
            System.out.println(modifyId + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        wiseSayingService.modify(wiseSaying, author, content);
    }
}
