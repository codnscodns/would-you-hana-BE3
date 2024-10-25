package com.hanaro.wouldyouhana.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customer_id;

    @ManyToOne // 다대일 관계 설정
    @JoinColumn(name = "category_id", nullable = false) // 외래 키 설정
    private Category category; // Category 객체 추가

    private String title;
    private String content;
    private String location;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer likeCount;
    private Integer scrapCount;
    private Integer viewCount;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private Answer answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Comment> comments;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "question_id")  // 외래 키 설정
    private List<Image> images;

//    public void addAnswers(Answer answer) {
//        answers.add(answer);
//    }
//
//    public void removeAnswers(Answer answer) {
//        answers.remove(answer);
//    }

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setQuestion(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
        comment.setQuestion(null);
    }
}
