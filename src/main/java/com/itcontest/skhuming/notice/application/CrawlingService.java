package com.itcontest.skhuming.notice.application;

import com.itcontest.skhuming.notice.domain.Notice;
import com.itcontest.skhuming.notice.domain.repository.NoticeRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CrawlingService {
    private final NoticeRepository noticeRepository;
    private final String BASE_URL = "https://www.skhu.ac.kr";
    private final String URL = BASE_URL + "/skhu/4198/subview.do";

    public CrawlingService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public void getNoticeData() throws IOException {
        List<Notice> notices = new ArrayList<>();

        Document document = Jsoup.connect(URL).get();

        for (int page = 1; page <= 5; page++) {
            Elements noticeElement = document.select("table.board-table.horizon1 tbody tr");

            for (Element content : noticeElement) {
                // 번호
                String number = content.select("td.td-num").text();

                // 일반 공지 (중요 공지) 제외
                if (number.equals("일반공지")) {
                    continue;
                }

                // 상태
                String status = content.select("td.td-state").text();
                boolean statusBoolean = "진행중".equals(status); // true가 진행중

                // 제목
                String title = content.select("td.td-subject strong").text();

                // 작성일
                String writeDate = content.select("td.td-date").text();

                // 작성자
                String author = content.select("td.td-write").text();

                // 공지사항 url
                Element linkElement = content.select("a").first();
                String relativeUrl = linkElement.attr("href");

                // BASE_URL(기본 https) + relativeUrl
                String fullUrl = BASE_URL + relativeUrl;

                // 상세 공지 내용
                StringBuilder contents = new StringBuilder();
                Document detailDocument = Jsoup.connect(fullUrl).get();
                Elements contentElements = detailDocument.select("._fnctWrap .view-con");

                // <p>태그로 구분하여 줄바꿈
                for (Element element : contentElements.select("p")) {
                    contents.append(element.text()).append("\n");
                }

                for (Element element : contentElements.select("h3")) {
                    contents.append(element.text()).append("\n");
                }

                Notice notice = new Notice(
                        title,
                        contents.toString(),
                        writeDate,
                        fullUrl,
                        statusBoolean,
                        author);

                if (!contents.toString().trim().isEmpty() && !noticeRepository.existsByTitleAndContents(title, contents.toString())) {
                    notices.add(notice);
                }
            }
            // 다음 페이지로 이동하기 위해 URL 변경
            if (page < 5) {
                String nextPageUrl = BASE_URL + "/skhu/4198/subview.do?page=" + (page + 1);
                document = Jsoup.connect(nextPageUrl).get();
            }
        }

        if (!notices.isEmpty()) {
            noticeRepository.saveAll(notices);
        }
    }
}
