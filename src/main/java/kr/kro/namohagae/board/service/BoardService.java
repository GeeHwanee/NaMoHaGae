package kr.kro.namohagae.board.service;

import kr.kro.namohagae.board.dao.BoardDao;
import kr.kro.namohagae.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    BoardDao boardDao;

    public void boardFreeInsertData(Board board, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFileName(fileName);
        board.setFilePath("/files/" + fileName);
        boardDao.boardFreeInsertData(board);

    }

    public List<Board> boardFreeList() {
        return boardDao.boardFreeList();
    }

    public Board boardFreeReadData(Integer boardNo) {

        return boardDao.boardFreeReadData(boardNo);
    }

}
