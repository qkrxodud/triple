package triple.review.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.Review;
import triple.review.entitiy.ReviewStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static triple.review.entitiy.Attachment.createAttachment;
import static triple.review.entitiy.Review.createReview;


@Slf4j
@SpringBootTest
@Transactional
class AttachmentServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    PointService pointService;

    @Test
    public void 파일등록() {
        //given
        Review review1 = createReview("reviewUUIDtest", "test", "test", "좋아요!", ReviewStatus.ADD);
        Long saveReviewId = reviewService.save(review1);
        Attachment attachment1 = createAttachment("fileUUIDtest", review1);

        //when
        Attachment saveAttachment = attachmentService.save(attachment1);

        //then
        assertThat(attachment1.getFileUUID()).isEqualTo(saveAttachment.getFileUUID());
    }

    @Test
    public void 파일등록포인트() {
        //given
        Review review1 = createReview("reviewUUIDtest1", "test", "test", "좋아요!", ReviewStatus.ADD);
        reviewService.save(review1);

        Attachment attachment1 = createAttachment("fileUUIDtest1", review1);
        Attachment attachment2 = createAttachment("fileUUIDtest2", review1);
        Attachment attachment3 = createAttachment("fileUUIDtest3", review1);
        attachmentService.save(attachment1);
        attachmentService.save(attachment2);
        attachmentService.save(attachment3);

        //when
        List<Object[]> userPoint1 = pointService.findUserPoint(review1.getUserUUID());
        Object[] objects1 = userPoint1.get(0);

        //then
        assertThat(Integer.parseInt(objects1[1].toString())).isEqualTo(3);
    }

    @Test
    public void 리뷰ID로파일찾기() throws Exception {
        //given
        Review review1 = createReview("reviewUUIDtest1", "test", "test", "좋아요!", ReviewStatus.ADD);
        reviewService.save(review1);

        Attachment attachment1 = createAttachment("fileUUIDtest1", review1);
        Attachment attachment2 = createAttachment("fileUUIDtest2", review1);
        Attachment attachment3 = createAttachment("fileUUIDtest3", review1);
        attachmentService.save(attachment1);
        attachmentService.save(attachment2);
        attachmentService.save(attachment3);

        //when
        List<Attachment> byReviewId = attachmentService.findByReviewId(review1.getReviewId());

        //then
        assertThat(byReviewId.size()).isEqualTo(3);
    }

    @Test
    public void 파일변경하기() throws Exception {
        //given
        Review review1 = createReview("reviewUUIDtest1", "test", "test", "좋아요!", ReviewStatus.ADD);
        reviewService.save(review1);

        Attachment attachment1 = createAttachment("fileUUIDtest1", review1);
        attachmentService.save(attachment1);

        //when
        Review review = reviewService.findReview("reviewUUIDtest1", "test", "test");
        String [] fileUUid = new String[0];
        attachmentService.modAttachment(review, fileUUid);

        List<Attachment> byReviewId = attachmentService.findByReviewId(review1.getReviewId());
        //then
        assertThat(byReviewId.size()).isEqualTo(0);
    }


    @Test
    public void 리뷰ID로파일삭제하기() {
        //given
        Review review1 = createReview("reviewUUIDtest1", "test", "test", "좋아요!", ReviewStatus.ADD);
        reviewService.save(review1);

        Attachment attachment1 = createAttachment("fileUUIDtest1", review1);
        Attachment attachment2 = createAttachment("fileUUIDtest2", review1);
        Attachment attachment3 = createAttachment("fileUUIDtest3", review1);
        attachmentService.save(attachment1);
        attachmentService.save(attachment2);
        attachmentService.save(attachment3);

        //when
        attachmentService.deleteAttachment(review1);
        List<Attachment> byReviewId = attachmentService.findByReviewId(review1.getReviewId());

        //then
        assertThat(byReviewId.size()).isEqualTo(0);
    }



}