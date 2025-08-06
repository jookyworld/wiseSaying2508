package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    public void t2() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라
                작자미상
                종료
                """);

        assertThat(rs).contains("명언 : ").contains("작가 : ");
    }

    @Test
    @DisplayName("명언번호 증가")
    public void t4() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(rs).contains("1번 명언이 등록되었습니다.").contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    public void t5() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                """);

        assertThat(rs).contains("번호 / 작가 / 명언").contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("삭제")
    public void t6() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                목록
                """);
        assertThat(rs)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("수정")
    public void t7() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=3
                수정?id=1
                수정한 명언
                수정한 작가
                목록
                """);
        assertThat(rs)
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("1 / 수정한 작가 / 수정한 명언")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("목록?keywordType=content&keyword=과거")
    void t8() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                """);

        assertThat(rs)
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("목록?keywordType=author&keyword=작자")
    void t9() {
        String rs = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=author&keyword=작자
                """);

        assertThat(rs)
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("목록?keyword=이순신")
    void t10() {
        String rs = AppTestRunner.run("""
                등록
                나의 죽음을 적에게 알리지 말라.
                이순신
                등록
                진정한 해전의 왕은 이순신이다.
                넬슨제독
                목록?keyword=이순신
                """);

        assertThat(rs)
                .contains("2 / 넬슨제독 / 진정한 해전의 왕은 이순신이다.")
                .contains("1 / 이순신 / 나의 죽음을 적에게 알리지 말라.");
    }

    @Test
    @DisplayName("목록 : 한 페이지에 최대 5개만 노출된다.")
    void t11() {
        String rs = AppTestRunner.run(
                IntStream
                        .rangeClosed(1, 10)
                        .mapToObj(id -> "등록\n명언 %d\n작자미상 %d".formatted(id, id))
                        .collect(Collectors.joining("\n")) + "\n" + """
                        목록
                        """
        );

        assertThat(rs)
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .doesNotContain("5 / 작자미상 5 / 명언 5")
                .doesNotContain("4 / 작자미상 4 / 명언 4")
                .doesNotContain("3 / 작자미상 3 / 명언 3")
                .doesNotContain("2 / 작자미상 2 / 명언 2")
                .doesNotContain("1 / 작자미상 1 / 명언 1");
    }

    @Test
    @DisplayName("목록?page=2")
    void t12() {
        String rs = AppTestRunner.run(
                IntStream
                        .rangeClosed(1, 10)
                        .mapToObj(id -> "등록\n명언 %d\n작자미상 %d".formatted(id, id))
                        .collect(Collectors.joining("\n")) + "\n" + """
                        목록?page=2
                        """
        );

        assertThat(rs)
                .doesNotContain("10 / 작자미상 10 / 명언 10")
                .doesNotContain("9 / 작자미상 9 / 명언 9")
                .doesNotContain("8 / 작자미상 8 / 명언 8")
                .doesNotContain("7 / 작자미상 7 / 명언 7")
                .doesNotContain("6 / 작자미상 6 / 명언 6")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1");
    }

    @Test
    @DisplayName("목록?keyword=명언&page=2")
    void t13() {
        String rs = AppTestRunner.run(
                IntStream
                        .rangeClosed(1, 10)
                        .mapToObj(id -> "등록\n명언 %d\n작자미상 %d".formatted(id, id))
                        .collect(Collectors.joining("\n")) + "\n" + """
                        목록?keyword=명언&page=2
                        """
        );

        assertThat(rs)
                .doesNotContain("10 / 작자미상 10 / 명언 10")
                .doesNotContain("9 / 작자미상 9 / 명언 9")
                .doesNotContain("8 / 작자미상 8 / 명언 8")
                .doesNotContain("7 / 작자미상 7 / 명언 7")
                .doesNotContain("6 / 작자미상 6 / 명언 6")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1");
    }
}
