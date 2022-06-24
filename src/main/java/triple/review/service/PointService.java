package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Point;
import triple.review.entitiy.PointStatus;
import triple.review.repository.PointRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointRepository pointRepository;

    //회원 포인트 조회
    public List<Object[]> findUserPoint(String userUUID) {
        return pointRepository.userPointFindByUserUUID(userUUID);
    }

    // 전체 회원 포인트 조회
    public List<Object[]> findAllPoint() {
        return pointRepository.allPointFindByAll();
    }

    // 리뷰 ID로 포인트 찾기
    public  List<Optional<Point>> findPointByReviewId(Long reviewId) {
        return pointRepository.findPointByReview_ReviewIdAndPointStatus(reviewId, PointStatus.Y);
    }
}
