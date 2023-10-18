import lombok.SneakyThrows;
import model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.lang.reflect.Field;
import java.time.Year;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.junit.jupiter.api.Assertions.*;

class ModelCommentTest {

    public int mockId = 0;
    public String mockUserEmail = "mockUseremail";
    public String mockUserName = "mockUserName";
    public String mockUserName2 = "mockusername2";
    public int mockCommidityId = 1;
    public String mockText = "mockText";

    Comment comment;

    @BeforeEach
    void createMockComment() {
        this.comment = new Comment(this.mockId,
                this.mockUserEmail, this.mockUserName, this.mockCommidityId, this.mockText);
    }

    @SneakyThrows
    @Test
    void id123IsAssignedToComment() {
        int testedId = 123;
        var comment = new Comment(testedId,
                this.mockUserEmail, this.mockUserName, this.mockCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(testedId, comment.getId());
    }

    @SneakyThrows
    @Test
    void id00000000IsAssignedToComment() {
        int testedId = 00000000;
        var comment = new Comment(testedId, this.mockUserEmail, this.mockUserName, this.mockCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(testedId, comment.getId());
    }

    @Test
    void userEmailRoseIsAssignedToComment() throws NoSuchFieldException {
        var testedEmail = "Rose@gmal.com";
        var comment = new Comment(this.mockId,
                testedEmail, this.mockUserName, this.mockCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("userEmail");
        field.setAccessible(true);
        assertEquals(testedEmail, comment.getUserEmail());
    }

    @Test
    void userEmailWithoutGmailIsAssignedToComment() throws NoSuchFieldException {
        var testedEmail = "Rose.com";
        var comment = new Comment(this.mockId,
                testedEmail, this.mockUserName, this.mockCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("userEmail");
        field.setAccessible(true);
        assertEquals(testedEmail, comment.getUserEmail());
    }

    @Test
    void userEmailWithoutDomainIsAssignedToComment() throws NoSuchFieldException {
        var testedEmail = "Rose@gmail";
        var comment = new Comment(this.mockId,
                testedEmail, this.mockUserName, this.mockCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("userEmail");
        field.setAccessible(true);
        assertEquals(testedEmail, comment.getUserEmail());
    }

    @SneakyThrows
    @Test
    void commidityId10102IsAssignedToComment() {
        var testedCommidityId = 10102;
        var comment = new Comment(this.mockId,
                this.mockUserEmail, this.mockUserName, testedCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("commodityId");
        field.setAccessible(true);
        assertEquals(testedCommidityId, comment.getCommodityId());
    }

    @SneakyThrows
    @Test
    void commidityId00000000IsAssignedToComment() {
        var testedCommidityId = 00000000;
        var comment = new Comment(this.mockId,
                this.mockUserEmail, this.mockUserName, testedCommidityId, this.mockText);
        Field field = Comment.class.getDeclaredField("commodityId");
        field.setAccessible(true);
        assertEquals(testedCommidityId, comment.getCommodityId());
    }

    @SneakyThrows
    @Test
    void textEmptyIsAssignedToComment() {
        var testedText = "";
        var comment = new Comment(this.mockId,
                this.mockUserEmail, this.mockUserName, this.mockCommidityId, testedText);
        Field field = Comment.class.getDeclaredField("text");
        field.setAccessible(true);
        assertEquals(testedText, comment.getText());
    }

    @SneakyThrows
    @Test
    void textSampleTextIsAssignedToComment() {
        var testedText = "SampleText";
        var comment = new Comment(this.mockId,
                this.mockUserEmail, this.mockUserName, this.mockCommidityId, testedText);
        Field field = Comment.class.getDeclaredField("text");
        field.setAccessible(true);
        assertEquals(testedText, comment.getText());
    }

    @SneakyThrows
    @Test
    void getCurrentYearReturnsCurrentTime() {
        String expectedDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(expectedDateFormat);

        var comment = this.comment;
        String commentCurrentDate = comment.getCurrentDate();

        Date parsedDate = null;
        parsedDate = dateFormat.parse(commentCurrentDate);

        long currentTime = System.currentTimeMillis();
        long parsedTime = parsedDate.getTime();
        long timeDifference = Math.abs(currentTime - parsedTime);

        assertTrue(timeDifference <= 1000);
    }

    @Test
    void addUserVotePutsMockUsername() {
        this.comment.addUserVote(this.mockUserName, "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertTrue(userVote.containsKey(this.mockUserName));
    }

    @Test
    void addUserVotePutsLikeField() {
        this.comment.addUserVote(this.mockUserName, "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals("like", userVote.get(this.mockUserName));
    }

    @Test
    void addUserVotePutsDislikeField() {
        this.comment.addUserVote(this.mockUserName, "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals("dislike", userVote.get(this.mockUserName));
    }

    @Test
    void defaultLikeNumberIsZero() {
        assertEquals(this.comment.getLike(), 0);
    }

    @Test
    void defaultDislikeNumberIsZero() {
        assertEquals(this.comment.getDislike(), 0);
    }

    @Test
    void addUserVoteGetsOneSampleWithMockUserNameAndLike() {
        this.comment.addUserVote(this.mockUserName, "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(userVote.size(), 1);
    }

    @Test
    void addUserVoteGetsOneSampleWithMockUserNameAndDislike() {
        this.comment.addUserVote(this.mockUserName, "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(userVote.size(), 1);
    }

    @Test
    void addUserVoteOverwritesSameMockUserWithLikeAndDislike() {
        this.comment.addUserVote(this.mockUserName, "dislike");
        this.comment.addUserVote(this.mockUserName, "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(1, userVote.size());
    }

    @Test
    void addUserVotePutsTwoMockUsersWithLike(){
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote(this.mockUserName, "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(2, userVote.size());
    }

    @Test
    void addUserVotePutsTwoMockUsersWithDislike(){
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote(this.mockUserName, "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(2, userVote.size());
    }

    @Test
    void addUserVotePutsTwoMockUsersWithOneDislikeAndLike(){
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote(this.mockUserName, "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(2, userVote.size());
    }

    @Test
    void addUserVotePutsFiveMockUsersWithLike(){
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(5, userVote.size());
    }

    @Test
    void addUserVotePutsFiveMockUsersWithDislike(){
        this.comment.addUserVote(this.mockUserName, "dislike");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "dislike");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(5, userVote.size());
    }

    @Test
    void addUserVotePutsElevenMockUsersWithLike(){
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        this.comment.addUserVote("mockUsername6", "like");
        this.comment.addUserVote("mockUsername7", "like");
        this.comment.addUserVote("mockUsername8", "like");
        this.comment.addUserVote("mockUsername9", "like");
        this.comment.addUserVote("mockUsername10", "like");
        this.comment.addUserVote("mockUsername11", "like");

        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(11, userVote.size());
    }

    @Test
    void addUserVotePutsElevenMockUsersWithDislike(){
        this.comment.addUserVote(this.mockUserName, "dislike");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "dislike");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "dislike");
        this.comment.addUserVote("mockUsername6", "dislike");
        this.comment.addUserVote("mockUsername7", "dislike");
        this.comment.addUserVote("mockUsername8", "dislike");
        this.comment.addUserVote("mockUsername9", "dislike");
        this.comment.addUserVote("mockUsername10", "dislike");
        this.comment.addUserVote("mockUsername11", "dislike");

        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(11, userVote.size());
    }

    @Test
    void addUserVote5MockUsernamesWithLikeAnd6WithDislike() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        this.comment.addUserVote("mockUsername6", "dislike");
        this.comment.addUserVote("mockUsername7", "dislike");
        this.comment.addUserVote("mockUsername8", "dislike");
        this.comment.addUserVote("mockUsername9", "dislike");
        this.comment.addUserVote("mockUsername10", "dislike");
        this.comment.addUserVote("mockUsername11", "dislike");

        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(11, userVote.size());
    }

    @Test
    void addUserVoteCounts5likesOutOfWholeEleven() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        this.comment.addUserVote("mockUsername6", "dislike");
        this.comment.addUserVote("mockUsername7", "dislike");
        this.comment.addUserVote("mockUsername8", "dislike");
        this.comment.addUserVote("mockUsername9", "dislike");
        this.comment.addUserVote("mockUsername10", "dislike");
        this.comment.addUserVote("mockUsername11", "dislike");

        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getLike(), 5);
    }

    @Test
    void addUserVoteCounts6dislikesOutOfWholeEleven() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        this.comment.addUserVote("mockUsername6", "dislike");
        this.comment.addUserVote("mockUsername7", "dislike");
        this.comment.addUserVote("mockUsername8", "dislike");
        this.comment.addUserVote("mockUsername9", "dislike");
        this.comment.addUserVote("mockUsername10", "dislike");
        this.comment.addUserVote("mockUsername11", "dislike");

        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getDislike(), 6);
    }

    @Test
    void addUserVoteCounts0LikesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "dislike");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "dislike");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getLike(), 0);
    }

    @Test
    void addUserVoteCounts5DislikesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "dislike");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "dislike");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "dislike");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getDislike(), 5);
    }

    @Test
    void addUserVoteCounts0DislikesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getDislike(), 0);
    }

    @Test
    void addUserVoteCounts5likesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "like");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "like");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getLike(), 5);
    }

    @Test
    void addUserVoteCounts2DislikesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getDislike(), 2);
    }

    @Test
    void addUserVoteCounts3likesOutOfWholeFive() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "dislike");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getLike(), 3);
    }

    @Test
    void addUserVoteCounts3likesOutOfWholeFiveWithOneInvalidVote() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "love");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getLike(), 3);
    }

    @Test
    void addUserVoteCounts1DislikesOutOfWholeFiveWithOneInvalidVote() {
        this.comment.addUserVote(this.mockUserName, "like");
        this.comment.addUserVote(this.mockUserName2, "love");
        this.comment.addUserVote("mockUsername3", "like");
        this.comment.addUserVote("mockUsername4", "dislike");
        this.comment.addUserVote("mockUsername5", "like");
        Map<String, String> userVote = this.comment.getUserVote();
        assertEquals(this.comment.getDislike(), 1);
    }

    @Test
    void addUserVotesCountsZeroLikesOutOfEmptyMap() {
        assertEquals(this.comment.getLike(), 0);
    }

    @Test
    void addUserVotesCountsZeroDislikesOutOfEmptyMap() {
        assertEquals(this.comment.getDislike(), 0);
    }

}


