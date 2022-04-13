//package jsbdy.mission3.repository;
//
//import jsbdy.mission3.model.BoardDto;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//@Repository
//public class InMemoryBoardRepository implements BoardRepository{
//    private Long lastIndex=0L;
//    //데이터들은 모두 (키, 값)의 1:1 구조로 되어있는 Entry
//    private final Map<Long, BoardDto> memory= new HashMap<>();
//
//    @Override
//    public BoardDto create(BoardDto dto) {
//        lastIndex++;
//        dto.setId(lastIndex);
//        memory.put(lastIndex, dto);
//        return dto;
//    }
//
//    @Override
//    public BoardDto read(Long id) {
//        return memory.getOrDefault(id,null);
//    }
//
//    @Override
//    public Collection<BoardDto> readAll() {
//        return memory.values();
//    }
//
//    @Override
//    public boolean update(Long id, BoardDto dto) {
//        if(memory.containsKey(id)){
//            dto.setId(id);
//            memory.put(id,dto);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        if(memory.containsKey(id)){
//            memory.remove(id);
//            return true;
//        }
//        return false;
//    }
//}
