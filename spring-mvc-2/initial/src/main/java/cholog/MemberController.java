package cholog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/members")
    public ResponseEntity<Void> create(@RequestBody Member member) {
        // TODO: member 정보를 받아서 생성한다.
        Member m = Member.toEntity(member, index.getAndIncrement());
        members.add(m);
        return ResponseEntity.created(URI.create("/members/" + m.getId())).build();
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> read() {
        // TODO: 저장된 모든 member 정보를 반환한다.
        List<Member> memberList = members
                .stream()
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(memberList);

    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Void> update(@RequestBody Member member, @PathVariable Long id) {
        // TODO: member의 수정 정보와 url 상의 id 정보를 받아 member 정보를 수정한다.
        Member m1 = members.stream()
            .filter(m -> m.getId().equals(id))
            .findFirst()
            .orElseThrow(RuntimeException::new);
        m1.update(member);
        return null;
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 member를 삭제한다.
        Member member = members.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        members.remove(member);

        if(members.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
        else
            return ResponseEntity.status(HttpStatus.OK).build();

    }
}
