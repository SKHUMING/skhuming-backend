package com.itcontest.skhuming.mileage.domain;

import com.itcontest.skhuming.member.domain.MemberHistoryMileage;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Mileage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_id")
    private Long mileageId;

    private String title;

    private int mileageScore;

    @OneToMany(mappedBy = "mileage")
    private List<MemberHistoryMileage> member = new ArrayList<>();

    protected Mileage() {

    }

    public Mileage(Long mileageId, String title, int mileageScore) {
        this.mileageId = mileageId;
        this.title = title;
        this.mileageScore = mileageScore;
    }

}
