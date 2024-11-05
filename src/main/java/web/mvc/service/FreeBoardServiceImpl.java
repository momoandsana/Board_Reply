package web.mvc.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.FreeBoard;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.FreeBoardRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository boardRepository;
    private final ModelMapper modelMapper;

//    @Override
//    public List<FreeBoardDTO> selectAll() {
//        return boardRepository.findAll().stream()
//                .map(FreeBoardDTO::from)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<FreeBoard> selectAll() {
        return boardRepository.findAll();
    }

    @Override
    public Page<FreeBoardDTO> selectAll(Pageable pageable) {
//        return boardRepository.findAll(pageable)
//                .map(FreeBoardDTO::from);

        //return boardRepository.findAll(pageable).map(board -> modelMapper.map(board, FreeBoardDTO.class));//n+1 문제있음
       Page<FreeBoard>freeBoardPage=boardRepository.join04(pageable);
        Page<FreeBoardDTO> freeBoardDTOPage = freeBoardPage.map(board -> modelMapper.map(board, FreeBoardDTO.class));

        return freeBoardDTOPage;
    }

//    @Override
//    public Page<FreeBoard> selectAll(Pageable pageable) {
//        return boardRepository.findAll(pageable);
//    }

    @Override
    public void insert(FreeBoardDTO boardDTO) {
        //FreeBoard board = FreeBoard.from(boardDTO);

        FreeBoard board= modelMapper.map(boardDTO, FreeBoard.class);// appConfig 에 해당함수 만들기
        boardRepository.save(board);
    }

    @Override
    public FreeBoardDTO selectBy(Long bno, boolean state) {// 조회수가 늘어야 하는 순간이 있고 조회수가 늘지 않아야 하는 순간이 있다
        FreeBoard freeBoard = boardRepository.findById(bno)
                .orElseThrow(() -> new BasicException(ErrorCode.FAILED_DETAIL));

        if (state) {
            freeBoard.setReadnum(freeBoard.getReadnum() + 1);
            /*
            변경감지가 일어나서 save 할 필요가 없음

             */
            //boardRepository.save(freeBoard);
        }

        FreeBoardDTO freeBoardDTO=modelMapper.map(freeBoard, FreeBoardDTO.class);
        /*
        엔티티를 dto 로 변환
         */

        //return FreeBoardDTO.from(freeBoard);
        return freeBoardDTO;
    }

    @Override
    public FreeBoardDTO update(FreeBoardDTO boardDTO) {
        FreeBoard existingBoard = boardRepository.findById(boardDTO.getBno())
                .orElseThrow(() -> new BasicException(ErrorCode.NOTFOUND_ID));

        if (boardDTO.getSubject() != null) {
            existingBoard.setSubject(boardDTO.getSubject());
        }
        if (boardDTO.getContent() != null) {
            existingBoard.setContent(boardDTO.getContent());
        }
        if (boardDTO.getWriter() != null) {
            existingBoard.setWriter(boardDTO.getWriter());
        }
        if (boardDTO.getPassword() != null) {
            existingBoard.setPassword(boardDTO.getPassword());
        }

        FreeBoard updatedBoard = boardRepository.save(existingBoard);
        FreeBoardDTO freeBoardDTO=modelMapper.map(updatedBoard, FreeBoardDTO.class);
        return freeBoardDTO;
    }

    @Override
    public void delete(Long bno, String password) {
        FreeBoard board = boardRepository.findById(bno)
                .orElseThrow(() -> new BasicException(ErrorCode.NOTFOUND_ID));
        if (!board.getPassword().equals(password)) {
            throw new BasicException(ErrorCode.FAILED_DETAIL);
        }

        boardRepository.deleteById(bno);
    }
}
