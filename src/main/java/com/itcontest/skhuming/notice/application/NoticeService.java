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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NoticeService {

    private static final String TILDE = "~";

    private LocalDate now;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final MemberScrapNoticeRepository memberScrapNoticeRepository;

    public NoticeService(NoticeRepository noticeRepository, MemberRepository memberRepository, MemberScrapNoticeRepository memberScrapNoticeRepository) {
        this.noticeRepository = noticeRepository;
        this.memberRepository = memberRepository;
        this.memberScrapNoticeRepository = memberScrapNoticeRepository;
    }

    /**
     * 세부 공지
     */
    public DetailsNoticeResDto detailsNoticeResponse(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        List<Long> memberIdList = new ArrayList<>();
        for (Member member : notice.getScrapMember()) {
            memberIdList.add(member.getMemberId());
        }

        String[] split = notice.getSchedule().split("~");
        String schedule = split[0] + " ~ " + split[1];

        return new DetailsNoticeResDto(notice.getNoticeId(),
                notice.getTitle(),
                schedule,
                notice.getContents(),
                notice.getMileageScore(),
                notice.getCreateDate(),
                notice.getLinks(),
                memberIdList);
    }

    /**
     * 공지 리스트
     */
    public Page<NoticeListResDto> noticeList(int page, int size) {
        Page<Notice> noticeSearchPage = noticeRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeId")));

        return noticeSearchPage.map(this::mapToNotice);
    }

    /**
     * 검색 공지 리스트
     */
    public Page<NoticeListResDto> noticeSearchList(String searchKeyword, int page, int size) {
        Page<Notice> noticeSearchPage = noticeRepository.findByTitleContaining(
                searchKeyword.trim(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeId")));

        return noticeSearchPage.map(this::mapToNotice);
    }

    private NoticeListResDto mapToNotice(Notice notice) {
        updateLocalDate();
        String systemTime = now.format(formatter);

        String[] splitDate = notice.getSchedule().split(TILDE);
        String date = getToStringDate(splitDate[1]);
        String startYear = splitDate[0].substring(0, 4);
        String endYear = splitDate[1].substring(0, 4);

        // 종료 : true, 진행중 : false
        boolean end = Integer.parseInt(date) < Integer.parseInt(systemTime);

        // 연도 구분
        if (Integer.parseInt(startYear) < Integer.parseInt(endYear)) {
            end = false;
        }

        return new NoticeListResDto(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getCreateDate(),
                end);
    }

    /**
     * 마이 스크랩 리스트
     */
    public List<NoticeListResDto> myPageScrapNoticeList(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        List<MemberScrapNotice> memberScrapNoticeList = memberScrapNoticeRepository.findByMember(member);

        return memberScrapNoticeList.stream()
                .map(this::mapToMyScrapNotice)
                .collect(Collectors.toList());
    }

    /**
     * 마이 스크랩 페이징 리스트
     */
    public Page<NoticeListResDto> myScrapNoticeList(Long memberId, int page, int size) {
        SecurityUtil.memberTokenMatch(memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        Page<MemberScrapNotice> myScrapNoticePage = memberScrapNoticeRepository.findByMyScrapNotice(
                member, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

        return myScrapNoticePage.map(this::mapToMyScrapNotice);
    }

    private NoticeListResDto mapToMyScrapNotice(MemberScrapNotice memberScrapNotice) {
        updateLocalDate();
        String systemTime = now.format(formatter);

        String[] splitDate = memberScrapNotice.getNotice().getSchedule().split(TILDE);
        String date = getToStringDate(splitDate[1]);
        String startYear = splitDate[0].substring(0, 4);
        String endYear = splitDate[1].substring(0, 4);

        // 종료 : true, 진행중 : false
        boolean end = Integer.parseInt(date) < Integer.parseInt(systemTime);

        // 연도 구분
        if (Integer.parseInt(startYear) < Integer.parseInt(endYear)) {
            end = false;
        }

        return new NoticeListResDto(
                memberScrapNotice.getNotice().getNoticeId(),
                memberScrapNotice.getNotice().getTitle(),
                memberScrapNotice.getNotice().getCreateDate(),
                end);
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

    /**
     * 공지 스크랩
     */
    @Transactional
    public void noticeScrap(Long memberId, Long noticeId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        member.addScrapNotice(notice);
        memberRepository.save(member);
    }

    /**
     * 공지 스크랩 취소
     */
    @Transactional
    public void noticeScrapCancel(Long memberId, Long noticeId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        member.cancelScrapNotice(notice);
        memberRepository.save(member);
    }

}
