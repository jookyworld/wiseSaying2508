package com.ll;

import com.ll.domain.WiseSaying.controller.WiseSayingController;
import com.ll.domain.system.controller.SystemController;
import com.ll.global.rq.Rq;

import java.util.Scanner;

public class App {

    private final Scanner scanner;

    public App() {
        this.scanner = AppContext.scanner;
    }

    public void run() {
        SystemController systemController = AppContext.systemController;
        WiseSayingController wiseSayingController = AppContext.wiseSayingController;

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            Rq rq = new Rq(cmd);
            switch (rq.getActionName()) {
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionList(rq);
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionModify(rq);
                case "종료" -> {
                    systemController.actionExit();
                    return;
                }
            }
        }
    }
}
