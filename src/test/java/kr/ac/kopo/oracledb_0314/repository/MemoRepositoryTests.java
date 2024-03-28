package kr.ac.kopo.oracledb_0314.repository;

import kr.ac.kopo.oracledb_0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

//    MemoRepository 의 save(Memo Entity 객체츼 참조값)를 호출하여 insert 한다.
    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test " + i).build();
            memoRepository.save(memo);
        });
    }

//    MemoRepository 의 fineById(Memo Entity 객체의 Id로 설정된 필드값)를 호출하여 select 한다.
//    fineById() 가 호출되면 바로 select 문을 실행한다.
//    findById()는 NullPointerException 이 발생되지 않도록 Null 체크를 한다.
    @Test
    public void testSelect(){
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===========================================");

        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

//    MemoRepository 의 fineById(Memo Entity 객체의 Id로 설정된 필드값)를 호출하여 select 한다.
//    getOne() 은 호출되면 바로 실행되지 않고 Memo Entity 가 필요할 때 select 문을 실행한다.
    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100l;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("===========================================");


            System.out.println(memo);

        }

//    MemoRepository 의 save(Memo Entity 객체의 참조값)를 호출하여 update 한다.
//    save() 는 호출하면 먼저 select 를 하기 때문에 기존에 Entity 가 있을 때는 update 를 실행한다.
    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(95L).memoText("Update Dummy Data 95").build();

        Memo memo1 = memoRepository.save(memo);

        System.out.println(memo1);
    }

//    MemoRepository 의 deleteById(Memo Entity 의 mno 값) 를 호출하여 delete 한다.
    @Test
    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
//         1페이지당 10개의 Entity
        Pageable pageable = PageRequest.of(1, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }

        System.out.println("===============================================");

        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Total Number: " + result.getNumber());
        System.out.println("Total Size: " + result.getSize());
        System.out.println("Has next page?: " + result.hasNext());
        System.out.println("Is first page?: " + result.isFirst());

    }

    @Test
    public void testSort() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo.getMno() + ", content: " + memo.getMemoText());
        });
    }

}
