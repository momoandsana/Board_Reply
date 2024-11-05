package web.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.mvc.domain.FreeBoard;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.FreeBoardRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository boardRepository;

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
        return boardRepository.findAll(pageable)
                .map(FreeBoardDTO::from);
    }

//    @Override
//    public Page<FreeBoard> selectAll(Pageable pageable) {
//        return boardRepository.findAll(pageable);
//    }

    @Override
    public void insert(FreeBoardDTO boardDTO) {
        FreeBoard board = FreeBoard.from(boardDTO);
        boardRepository.save(board);
    }

    @Override
    public FreeBoardDTO selectBy(Long bno, boolean state) {
        FreeBoard freeBoard = boardRepository.findById(bno)
                .orElseThrow(() -> new BasicException(ErrorCode.FAILED_DETAIL));

        if (state) {
            freeBoard.setReadnum(freeBoard.getReadnum() + 1);
            boardRepository.save(freeBoard);
        }

        return FreeBoardDTO.from(freeBoard);
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
        return FreeBoardDTO.from(updatedBoard);
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
