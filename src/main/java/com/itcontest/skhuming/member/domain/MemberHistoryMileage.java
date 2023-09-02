package com.itcontest.skhuming.member.domain;

import com.itcontest.skhuming.mileage.domain.Mileage;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberHistoryMileage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String systemDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "mileage_id")
    private Mileage mileage;

    protected MemberHistoryMileage() {

    }

    public MemberHistoryMileage(Member member, Mileage mileage, String systemDate) {
        this.member = member;
        this.mileage = mileage;
        this.systemDate = systemDate;
    }
}
