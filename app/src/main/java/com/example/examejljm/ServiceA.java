package com.example.examejljm;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ServiceA {
    @GET("journals.php")
    Call<List<Revista>> getJournals();

    @GET("issues.php")
    Call<List<Issue>> getIssues(@Query("j_id") int journalId);

    @GET("pubs.php")
    Call<List<Article>> getArticles(@Query("i_id") int issueId);
}
