package ru.job4j.socialmedia.repository;

import ru.job4j.socialmedia.model.*;

import java.util.UUID;

public class TestDataFactory {

    private TestDataFactory() {
    }

    private static String randomString() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public static User createUser() {
        String randomString = randomString();
        User user = new User();
        user.setUsername(randomString);
        user.setEmail(randomString + "@mail.com");
        user.setPasswordHash(randomString);
        return user;
    }

    public static Subscription createSubscription(User user, User subscriber) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriber(subscriber);
        return subscription;
    }

    public static PostImage createPostImage(Post post) {
        PostImage postImage = new PostImage();
        postImage.setPath(randomString());
        postImage.setPost(post);
        return postImage;
    }

    public static Post createPost(User author) {
        Post post = new Post();
        post.setTitle(randomString());
        post.setBody(randomString());
        post.setAuthor(author);
        return post;
    }

    public static Message createMessage(User sender, User receiver) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(randomString());
        return message;
    }

    public static Friendship createFriendship(User user1, User user2) {
        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        return friendship;
    }

    public static FriendRequest createFriendRequest(User sender, User receiver) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        return friendRequest;
    }
}
