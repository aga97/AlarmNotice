package me.jungwoo.pagealarm;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class fbServiceImpl implements fbService{

    public static final String COLLECTION_NAME="NoticeBoard";

    @Override
    public String insertMember(Member member) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture =firestore.collection(COLLECTION_NAME).document(member.getId()).set(member);

        return apiFuture.get().getUpdateTime().toString();
    }

    @Override
    public Member getMemberDetail(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        Member member = null;
        if(documentSnapshot.exists()) {
            member = documentSnapshot.toObject(Member.class);
            return member;
        } else {
            return null;
        }
    }

    @Override
    public String updateMember(Member member) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture =firestore.collection(COLLECTION_NAME).document(member.getId()).set(member);

        return  apiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String deleteMember(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();

        return "Document id :" + id + "delete";
    }

    @Override
    public String createMember(String id, String date, String title, String text) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
        Map<String, Object> data = new HashMap<>();
        data.put("date", date);
        data.put("title", title);
        data.put("text", text);
        ApiFuture<WriteResult> result = documentReference.set(data);

        return "Update time : " + result.get().getUpdateTime();
    }

    @Override
    public String getNotice(String id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        Query query = collectionReference.whereEqualTo("title", id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        String str = "";

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            str = document.getString("text") + "\n작성일 : \n" + document.getString("date");
        }


        return str;
    }

    @Override
    public String getAllNotice() throws Exception {
        String str = "";
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {

            str = str + "\n" +document.getId();

        }

        return str;
    }

}
