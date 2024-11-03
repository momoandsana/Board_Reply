package web.mvc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.domain.Reply;
import web.mvc.repository.ReplyRepository;

@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public void insert(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }
}
