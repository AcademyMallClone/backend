package com.korea.it.shopping.notice.entity;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="date")
    private String date;

    @Column(name="content")
    private String content = "내용 없음"; // 기본값 설정


}


