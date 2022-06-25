package triple.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import triple.review.dto.PointDto;
import triple.review.service.PointService;
import triple.review.utils.ResponseMessage;

import java.util.List;
import java.util.stream.Collectors;

import static triple.review.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class PointApiController {

    private final PointService pointService;

    //유저 포인트 조회
    @GetMapping("/api/find-user-point")
    public ResponseEntity findUserPoint(@RequestParam(value = "userUUID", defaultValue = "0") String userUUID) {
        List<Object[]> findUserPoint = pointService.findUserPoint(userUUID);
        return getResponseEntity(findUserPoint);
    }

    //전체 포인트 조회
    @GetMapping("/api/find-all-point")
    public ResponseEntity findAllPoint() {
        List<Object[]> findUserPoint = pointService.findAllPoint();
        return getResponseEntity(findUserPoint);
    }

    //응답 데이터
    private ResponseEntity getResponseEntity(List<Object[]> findUserPoint) {
        List<PointDto> findPointDtos = findUserPoint.stream().map(
                o -> new PointDto(o[0].toString()
                        ,Integer.parseInt(o[1].toString())))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", findPointDtos), HttpStatus.OK);
    }
}
