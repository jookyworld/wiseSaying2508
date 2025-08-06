package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {

    @Test
    @DisplayName("== 명언 앱 == 출력")
    public void t1() {
        String rs = AppTestRunner.run("");

        assertThat(rs).contains("== 명언 앱 ==").contains("명령) ");
    }
}
