package web.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.mvc.domain.FreeBoard;
import web.mvc.repository.FreeBoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository boardRepository;

    @Override
    public List<FreeBoard> selectAll() {
        return boardRepository.findAll();
    }

    @Override
    public Page<FreeBoard> selectAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Override
    public void insert(FreeBoard board) {
        boardRepository.save(board);
    }

    @Override
    public FreeBoard selectBy(Long bno, boolean state) {
        Optional<FreeBoard> optionalBoard = boardRepository.findById(bno);
        if(optionalBoard.isPresent())
        {
            FreeBoard freeBoard = optionalBoard.get();
            if(state)
            {
                freeBoard.setReadnum(freeBoard.getReadnum() + 1);
                boardRepository.save(freeBoard);
            }
            return freeBoard;
        }
        return null;
    }

    @Override
    public FreeBoard update(FreeBoard board) {
        return boardRepository.save(board);// save 가 업데이트도 한다
    }

    @Override
    public void delete(Long bno, String password) {
        boardRepository.deleteById(bno);
    }
}
