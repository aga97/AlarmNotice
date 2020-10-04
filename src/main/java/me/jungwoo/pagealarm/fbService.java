package me.jungwoo.pagealarm;



public interface fbService {

    public String insertMember(Member member) throws Exception;

    public Member getMemberDetail(String id) throws Exception;

    public String updateMember(Member member) throws Exception;

    public String deleteMember(String id) throws Exception;

    public String createMember(String id, String date, String title, String text) throws  Exception;

    public String getNotice(String id) throws Exception;

    public String getAllNotice() throws Exception;

}
