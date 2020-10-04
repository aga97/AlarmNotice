package me.jungwoo.pagealarm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class Parsing  {

    @Autowired
    fbService fbServ;

    public String ParsePage() {
        //홈페이지 접속 , 공지사항 각 링크로 접속후, 제목, 작성일, 내용 가져온뒤 fb에 저장
        //
        String url = "http://www.bongmu.es.kr/board/list.do?boardId=BBS_0000001&menuCd=MCD_000000000000021169";
        Document doc = null;
        String str = "";
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Elements el = doc.select(".left").select("a");
        for(Element el2 : el.select("[href]")) {
            getPage(el2.attr("abs:href"));
        }

        return "문제없음";
    }

    public void getPage(String url) {
        //개별 페이지에서 페이지 정보 얻어오기
        Document doc = null;
        int count = 0;
        String title;
        String date = "";
        String text = "";

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //제목
        Elements el = doc.select(".rfc_bbs_list_last");
        title = el.text();

        //등록일
        for(Element element : doc.select(".rfc_bbs_see_txt_left")) {
            if(count == 1) {
                date = element.text();
            }
            count++;
        }

        //내용
        el = doc.select(".rfc_bbs_see_txt_left_con").select("[style]");
        for(Element element : el.select("p")) text = text + "\n" + element.text();


        //업데이트
        try {
            fbServ.createMember(title, date, title, text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
