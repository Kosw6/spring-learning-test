package cholog.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "autowiredBean")
public class AutowiredBean {
    /*
    어떤 방법으로 Component에 Bean을 주입하는지 학습하기
     */
//    @Autowired//필드 주입
    private SpringBean springBean;

    @Autowired
    public void setSpringBean(SpringBean springBean) {
        this.springBean = springBean;
    }

//    public AutowiredBean(SpringBean springBean){//생성자 주입-주입받을 객체가 이미 빈으로 등록되어 있고 클래스의 생성자가 하나일때
//        this.springBean = springBean;//@Autowired생략가능, 롬복 사용시에는 주입받을 필드를 final객체로 만들어서
//    }//@RequireArgumentConstruct 어노테이션 사용가능->final이 붙은 불변필드를 주입받는 생성자를 자동으로 만들어준다.

    public String sayHello() {
        return springBean.hello();
    }


}
