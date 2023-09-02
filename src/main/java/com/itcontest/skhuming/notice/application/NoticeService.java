package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.global.util.SecurityUtil;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.member.domain.repository.MemberScrapNoticeRepository;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.notice.api.dto.response.DetailsNoticeResDto;
import com.itcontest.skhuming.notice.api.dto.response.NoticeListResDto;
import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class NoticeService {

    private static final String TILDE = "~";

    private LocalDate now;
    private DateTimeFormatter formatter;

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final MemberScrapNoticeRepository memberScrapNoticeRepository;

    public NoticeService(NoticeRepository noticeRepository, MemberRepository memberRepository, MemberScrapNoticeRepository memberScrapNoticeRepository) {
        this.noticeRepository = noticeRepository;
        this.memberRepository = memberRepository;
        this.memberScrapNoticeRepository = memberScrapNoticeRepository;
        formatter = DateTimeFormatter.ofPattern("MMdd");
    }

    public DetailsNoticeResDto detailsNoticeResponse(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        List<Long> memberIdList = new ArrayList<>();
        for (Member member : notice.getScrapMember()) {
            memberIdList.add(member.getMemberId());
        }

        return new DetailsNoticeResDto(notice.getNoticeId(),
                notice.getTitle(),
                notice.getSchedule(),
                notice.getContents(),
                notice.getMileageScore(),
                memberIdList);
    }

    public Page<NoticeListResDto> noticeList(int page, int size) {
        Page<Notice> noticeSearchPage = noticeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeId")));

        return noticeSearchPage.map(this::mapToNotice);
    }

    public Page<NoticeListResDto> noticeSearchList(String searchKeyword, int page, int size) {
        Page<Notice> noticeSearchPage = noticeRepository.findByTitleContaining(searchKeyword, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeId")));

        return noticeSearchPage.map(this::mapToNotice);
    }

    private NoticeListResDto mapToNotice(Notice notice) {
        updateLocalDate();
        String systemTime = now.format(formatter);

        String[] splitDate = notice.getSchedule().split(TILDE);
        String date = getToStringDate(splitDate[1]);

        boolean end = Integer.parseInt(date) < Integer.parseInt(systemTime);

        return new NoticeListResDto(notice.getNoticeId(), notice.getTitle(), end);
    }

    public Page<NoticeListResDto> myScrapNoticeList(Long memberId, int page, int size) {
        SecurityUtil.memberTokenMatch(memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        Page<MemberScrapNotice> myScrapNoticePage = memberScrapNoticeRepository.findByMyScrapNotice(member, PageRequest.of(page, size));

        return myScrapNoticePage.map(this::mapToMyScrapNotice);
    }

    private NoticeListResDto mapToMyScrapNotice(MemberScrapNotice MemberScrapNotice) {
        updateLocalDate();
        String systemTime = now.format(formatter);

        String[] splitDate = MemberScrapNotice.getNotice().getSchedule().split(TILDE);
        String date = getToStringDate(splitDate[1]);

        boolean end = Integer.parseInt(date) < Integer.parseInt(systemTime);

        return new NoticeListResDto(MemberScrapNotice.getNotice().getNoticeId(), MemberScrapNotice.getNotice().getTitle(), end);
    }

    private String getToStringDate(String schedule) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String date : schedule.substring(5).split("-")) {
                if (Integer.parseInt(date) < 10) {
                    sb.append("0");
                }
                sb.append(date);
            }
        } catch (Exception e) {
            throw new NumberFormatException("정수가 아닙니다.");
        }

        return sb.toString();
    }

    private void updateLocalDate() {
        this.now = LocalDate.now();
    }

    public void noticeScrap(Long memberId, Long noticeId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        member.addScrapNotice(notice);
        memberRepository.save(member);
    }

    public void noticeScrapCancel(Long memberId, Long noticeId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        member.cancelScrapNotice(notice);
        memberRepository.save(member);
    }

}
