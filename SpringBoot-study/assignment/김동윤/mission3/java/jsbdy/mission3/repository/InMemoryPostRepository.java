//package jsbdy.mission3.repository;
//
//import jsbdy.mission3.model.BoardDto;
//import jsbdy.mission3.model.PostDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class InMemoryPostRepository implements PostRepository {
//    private final BoardRepository boardRepository;
//
//    private Long lastIndex = 0L;
//    //데이터들은 모두 (키, 값)의 1:1 구조로 되어있는 Entry
//    private final Map<Long, PostDto> memory = new HashMap<>();
//
//    public InMemoryPostRepository(
//            //여기 오토와이어드 안 붙여도 되긴 함
//            @Autowired BoardRepository boardRepository){
//        this.boardRepository=boardRepository;
//    }
//
//    @Override
//    public PostDto create(Long boardId, PostDto dto) {
//        BoardDto boardDto = this.boardRepository.read(boardId);
//        if (boardDto==null){
//            return null;
//        }
//
//        dto.setBoardId(boardId);//post는 board가 있는지 여부도 점검하는 부분이 추가적으로 필요!
//
//        lastIndex++;
//        dto.setId(lastIndex);
//        memory.put(lastIndex, dto);
//
//        return dto;
//    }
//
//    @Override
//    public PostDto read(Long boardId, Long postId) {
//        PostDto postDto=memory.getOrDefault(postId, null);
//        if(postDto == null){
//            return null;
//        }
//
//        else if(!Objects.equals(postDto.getBoardId(),boardId)){
//            return null;
//        }
//
//        return postDto;
//    }
//
//    @Override
//
//    public Collection<PostDto> readAll(Long boardId) {
//        if (boardRepository.read(boardId)==null) return null;
//        //얘는 아무것도 없으면 null
//        //memory : Map<Long, PostDto>
//        Collection<PostDto> postList=new ArrayList<>();
//        memory.forEach((postId, postDto)-> {
//            if (Objects.equals(postDto.getBoardId(), boardId))
//                postList.add(postDto);
//                }
//                );
//        return postList;//얘는 아무것도 없어도 empty
//    }
//
//    @Override
//    public boolean update(Long boardId, Long postId, PostDto dto) {
//        PostDto targetPost = memory.getOrDefault(postId, null);
//        if (targetPost == null) {
//            return false;
//        } else if (!Objects.equals(targetPost.getBoardId(), boardId)) {
//            return false;
//        } else if (!Objects.equals(targetPost.getPassword(), dto.getPassword())) {
//            return false;
//        }
//
//        targetPost.setTitle(
//                dto.getTitle() == null ? targetPost.getTitle() : dto.getTitle()
//        );
//        targetPost.setContent(
//                dto.getContent() == null ? targetPost.getContent() : dto.getContent()
//        );
//
//        return true;
//    }
//
//
//    @Override
//    public boolean delete(Long boardId, Long postId, String password) {
//
//        PostDto targetPost = memory.getOrDefault(postId, null);
//
//        if (targetPost == null) {
//            return false;
//        } else if (!Objects.equals(targetPost.getBoardId(), boardId)) {
//            return false;
//        } else if (!Objects.equals(targetPost.getPassword(), password)) {
//            return false;
//        }
//        memory.remove(postId);
//        return true;
//    }
//}