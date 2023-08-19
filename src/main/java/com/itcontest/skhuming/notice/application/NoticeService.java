package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.jwt.SecurityUtil;
import com.itcontest.skhuming.member.domain.Member;
import com.itcontest.skhuming.member.domain.repository.MemberRepository;
import com.itcontest.skhuming.member.exception.NotFoundMemberException;
import com.itcontest.skhuming.notice.api.dto.response.DetailsNoticeResDto;
import com.itcontest.skhuming.notice.api.dto.response.NoticeListResDto;
import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    private LocalDate now;
    private DateTimeFormatter formatter;

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public NoticeService(NoticeRepository noticeRepository, MemberRepository memberRepository) {
        this.noticeRepository = noticeRepository;
        this.memberRepository = memberRepository;
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

    public List<NoticeListResDto> noticeList() {
        List<Notice> noticeList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeId"));

        return getNoticeListRes(noticeList);
    }

    public List<NoticeListResDto> noticeSearchList(String searchKeyword) {
        List<Notice> noticeSearchList = noticeRepository.findByTitleContaining(searchKeyword, Sort.by(Sort.Direction.DESC, "noticeId"));

        return getNoticeListRes(noticeSearchList);
    }

    private List<NoticeListResDto> getNoticeListRes(List<Notice> noticeList) {
        updateLocalDate();
        String SystemTime = now.format(formatter);

        List<NoticeListResDto> noticeListResDtoList = new ArrayList<>();
        for (Notice notice : noticeList) {
            boolean end = false;
            String[] splitDate = notice.getSchedule().split("~");
            String date = getToStringDate(splitDate[1]);

            if (Integer.parseInt(date) < Integer.parseInt(SystemTime)) {
                end = true;
            }

            NoticeListResDto noticeListResDto = new NoticeListResDto(notice.getNoticeId(), notice.getTitle(), end);
            noticeListResDtoList.add(noticeListResDto);
        }

        return noticeListResDtoList;
    }

    public List<NoticeListResDto> myScrapNoticeList(Long memberId) {
        SecurityUtil.memberTokenMatch(memberId);

        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        updateLocalDate();
        String SystemTime = now.format(formatter);

        List<NoticeListResDto> noticeListResDtoList = new ArrayList<>();
        for (Notice notice : member.getScrapNotices()) {
            boolean end = false;
            String[] splitDate = notice.getSchedule().split("~");
            String date = getToStringDate(splitDate[1]);

            if (Integer.parseInt(date) < Integer.parseInt(SystemTime)) {
                end = true;
            }

            NoticeListResDto noticeListResDto = new NoticeListResDto(notice.getNoticeId(), notice.getTitle(), end);
            noticeListResDtoList.add(noticeListResDto);
        }

        return noticeListResDtoList;
    }

    private String getToStringDate(String schedule) {
        StringBuilder sb = new StringBuilder();
        try {
            for (String date : schedule.split("/")) {
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
