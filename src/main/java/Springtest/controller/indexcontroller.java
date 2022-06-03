package Springtest.controller;

import Springtest.Entity.TestEntity;
import Springtest.Entity.TestRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")    // 현재 클래스는 home 매핑된다.
public class indexcontroller {

    @GetMapping("/main")
    public String home(){
        return "main";
    }

    @Autowired
    TestRepository testRepository;

    // 2. main.js 내 작성메소드가 요청하는 URL
    @GetMapping("/save")
    @ResponseBody   // Response(응답) Body(객체) : java 객체를 반환한다.
    public String getdata (HttpServletRequest request){
        // 1. 변수 요청
        String content = request.getParameter("content");
        // 2. 엔티티 생성
        TestEntity testEntity = new TestEntity();
        testEntity.content = content;
        // 3. 엔티티  DB에 저장 해주는 메소드 사용
        testRepository.save(testEntity);
        // 4. 반환
        return "작성성공";
    }

    // 3. main.js내 호출 메소드가 요청하는 URL 정의

    @GetMapping("/getlist")
    public void getlist(HttpServletResponse response){
        // 1. 모든 엔티티 호출하기
        List<TestEntity> testEntities = testRepository.findAll();   // 모든 엔티티 select
        System.out.println(testEntities);
        // 2. JSON 형식으로 변경
        JSONArray jsonArray = new JSONArray();
        for(TestEntity test : testEntities){
            JSONObject object = new JSONObject();
            object.put("no",test.no);
            object.put("content",test.content);
            jsonArray.put(object);
        }
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonArray);
        }catch(Exception e){e.printStackTrace();}

    }

    @GetMapping("/update")
    @ResponseBody
    @Transactional // 트랜잭션 어노테이션
    public int update(HttpServletRequest request){
        int no = Integer.parseInt(request.getParameter("no"));
        String content = request.getParameter("content");
        // 1. PK값 이용한 엔티티 찾기
        Optional<TestEntity> optionalTestEntity = testRepository.findById(no);
        // 2. Option 엔티티 빼오기
        TestEntity entity = optionalTestEntity.get();
        // 3. 수정
        System.out.println("수정 전  : "+entity.content);
        entity.content = content;
        System.out.println("수정 후  : "+entity.content);
        return 1;
    }

    @GetMapping("/delete1")
    @ResponseBody
    public int delete1(HttpServletRequest request){
        int no = Integer.parseInt(request.getParameter("no"));
        Optional<TestEntity> optionalTestEntity = testRepository.findById(no);
        TestEntity entity = optionalTestEntity.get();
        testRepository.delete(entity);
        return 1;
    }



    // @RequestMapping : 모든 URL 매핑 가능

    // @GetMapping : GET 메소드 URL 매핑 [ 요청변수 보인다 = 보안X 캐시O ]

    // @PostMapping : POST 메소드 URL 매핑 [ 요청변수 X = 보안O 캐시X ]

    /////////////////////// 스프링에서 지원하는 요청방식 구분 ////////////////////////

    // @PutMapping : PUT 메소드 URL 매핑 [ 삽입 , 수정 사용 ]

    // @DeleteMapping : DELETE 메소드 URL 매핑 [ 삭제시 사용 ]

    // @PathVariable : 경로에 변수를 바인딩(넘겨주기)

    // 멱등성 : 오청후에 서버에 상태를 남기기
    // 반복되는 많은 요청이 있을경우에 post 보다 put,delete 사용 권장


}
