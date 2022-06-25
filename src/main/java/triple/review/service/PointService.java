package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.repository.PointRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    //회원 포인트 조회
    public List<Object[]> findUserPoint(String userUUID) {
        return pointRepository.userPointFindByUserUUID(userUUID, "ADD");
    }

    // 전체 회원 포인트 조회
    public List<Object[]> findAllPoint() {
        return pointRepository.allPointFindByAll("ADD");
    }

}
