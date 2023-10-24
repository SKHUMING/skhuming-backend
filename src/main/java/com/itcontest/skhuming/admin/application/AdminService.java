package com.itcontest.skhuming.admin.application;

import com.itcontest.skhuming.admin.api.dto.request.NoticeSaveReqDto;
import com.itcontest.skhuming.member.domain.MemberScrapNotice;
import com.itcontest.skhuming.member.domain.repository.MemberScrapNoticeRepository;
import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import com.itcontest.skhuming.notice.exception.NotFoundNoticeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

    private final NoticeRepository noticeRepository;
    private final MemberScrapNoticeRepository memberScrapNoticeRepository;

    public AdminService(NoticeRepository noticeRepository, MemberScrapNoticeRepository memberScrapNoticeRepository) {
        this.noticeRepository = noticeRepository;
        this.memberScrapNoticeRepository = memberScrapNoticeRepository;
    }

    // 공지 등록
    public void noticeSave(NoticeSaveReqDto noticeSaveReqDto) {
        Notice notice = new Notice(
                noticeSaveReqDto.getTitle(),
                noticeSaveReqDto.getContents(),
                noticeSaveReqDto.getCreateDate(),
                noticeSaveReqDto.getAuthor(),
                true,
                noticeSaveReqDto.getLinks());

        noticeRepository.save(notice);
    }

    // 공지 수정
    public void noticeUpdate(Long noticeId, NoticeSaveReqDto noticeSaveReqDto) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);

        notice.update(
                noticeSaveReqDto.getTitle(),
                noticeSaveReqDto.getContents(),
                noticeSaveReqDto.getCreateDate(),
                noticeSaveReqDto.getLinks(),
                true,
                noticeSaveReqDto.getAuthor());
    }

    // 공지 삭제
    public void noticeDelete(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotFoundNoticeException::new);
        List<MemberScrapNotice> scrapNotice = memberScrapNoticeRepository.findByNotice(notice);

        memberScrapNoticeRepository.deleteAll(scrapNotice);
        noticeRepository.deleteById(noticeId);
    }

}
