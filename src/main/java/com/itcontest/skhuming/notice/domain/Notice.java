package com.itcontest.skhuming.notice.domain;

import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.notice.api.dto.request.NoticeSaveReqDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Notices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    private String title;

    private String schedule;

    private String contents;

    private int mileageScore;

    private String img;

    private int scrap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    private Notice(String title, String schedule, String contents, int mileageScore, String img, int scrap) {
        this.title = title;
        this.schedule = schedule;
        this.contents = contents;
        this.mileageScore = mileageScore;
        this.img = img;
        this.scrap = scrap;
    }

    public static Notice createNotice(NoticeSaveReqDto noticeSaveReqDto) {
        return Notice.builder()
                .title(noticeSaveReqDto.getTitle())
                .schedule(noticeSaveReqDto.getSchedule())
                .contents(noticeSaveReqDto.getContents())
                .mileageScore(noticeSaveReqDto.getMileageScore())
                .img(noticeSaveReqDto.getImg())
                .scrap(noticeSaveReqDto.getScrap())
                .build();
    }

}

