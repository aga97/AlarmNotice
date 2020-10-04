package me.jungwoo.pagealarm;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {
    //fb에서 저장, 끌어올 내용

    private String id;

    private String date;

    private String title;

    private String text;

}
