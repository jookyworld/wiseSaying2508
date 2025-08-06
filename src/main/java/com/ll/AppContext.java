package com.ll;

import com.ll.domain.WiseSaying.controller.WiseSayingController;
import com.ll.domain.WiseSaying.repository.WiseSayingRepository;
import com.ll.domain.WiseSaying.service.WiseSayingService;
import com.ll.domain.system.controller.SystemController;

import java.util.Scanner;

public class AppContext {
    public static Scanner scanner;
    public static SystemController systemController;
    public static WiseSayingRepository wiseSayingRepository;
    public static WiseSayingService wiseSayingService;
    public static WiseSayingController wiseSayingController;

    public static void renew(Scanner _scanner) {
        scanner = _scanner;
        systemController = new SystemController();
        wiseSayingRepository = new WiseSayingRepository();
        wiseSayingService = new WiseSayingService();
        wiseSayingController = new WiseSayingController();
    }

    public static void renew() {
        renew(new Scanner(System.in));

    }
}
