package Springtest.Entity;


import javax.persistence.*;

@Entity // 해당 클래스를 Entity[DB내 테이블과 매핑] 로 사용
@Table(name = "test")   // DB사용될 테이블 이름 정하기 생략시 클래스명
public class TestEntity { // 클래스

    @Id // 기본키 pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    public int no;  // 필드

    @Column(name = "content") // DB에서 사용할 필드명 정하기 생략가능
    public String content; // 필드

}

// jpa : 클래스를 DB 테이블처럼 사용하자
    // 1. JAVA 클래스를 엔티티화 하면 DB 테이블이  자동 생성된다.

    // java <-----JPA(매핑)-----> DB
    // entity클래스              table
    //  필드                      필드
    // 예) 게시물번호             게시물번호
    //    게시물내용              게시물내용
