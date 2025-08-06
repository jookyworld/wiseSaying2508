package com.ll.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilTest {

    @Test
    @DisplayName("파일 생성")
    void t1() {
        //given
        String filePath = "temp/text.txt";
        //when
        Util.file.touch(filePath);
        //then
        assertThat(Util.file.exists(filePath)).isTrue();
        Util.file.delete(filePath);
    }
}
