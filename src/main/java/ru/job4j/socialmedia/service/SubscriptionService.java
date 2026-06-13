package ru.job4j.socialmedia.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.*;
import ru.job4j.socialmedia.repository.*;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        validateDifferentUsers(senderId, receiverId);

        User sender = getUser(senderId);
        User receiver = getUser(receiverId);

        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver)
                || friendRequestRepository.existsBySenderAndReceiver(receiver, sender)) {
            throw new IllegalArgumentException("Friend request already exists");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(friendRequest);
        subscribe(sender, receiver);
    }

    @Transactional
    public void acceptFriendRequest(Long requestId, Long currentUserId) {
        FriendRequest friendRequest = getPendingFriendRequest(requestId, currentUserId);
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(friendRequest);

        User sender = friendRequest.getSender();
        User receiver = friendRequest.getReceiver();

        createFriendship(sender, receiver);
        subscribe(receiver, sender);
    }

    @Transactional
    public void rejectFriendRequest(Long requestId, Long currentUserId) {
        FriendRequest friendRequest = getPendingFriendRequest(requestId, currentUserId);
        friendRequest.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(friendRequest);
    }

    private FriendRequest getPendingFriendRequest(Long requestId, Long currentUserId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Friend request not found"));

        if (!friendRequest.getReceiver().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("You are not allowed to process this request");
        }
        if (friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalStateException("Friend request already processed");
        }
        return friendRequest;
    }

    private void createFriendship(User firstUser, User secondUser) {
        Friendship friendship = new Friendship();
        if (firstUser.getId() < secondUser.getId()) {
            friendship.setUser1(firstUser);
            friendship.setUser2(secondUser);
        } else {
            friendship.setUser1(secondUser);
            friendship.setUser2(firstUser);
        }
        friendshipRepository.save(friendship);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        validateDifferentUsers(userId, friendId);

        User user = getUser(userId);
        User friend = getUser(friendId);

        Friendship friendship = getFriendship(user, friend);
        friendshipRepository.delete(friendship);
        // user отписывается от friend
        unsubscribe(user, friend);
    }

    private void unsubscribe(User subscriber, User targetUser) {
        subscriptionRepository.deleteByUserAndSubscriber(targetUser, subscriber);
    }

    private void subscribe(User subscriber, User targetUser) {
        if (!subscriptionRepository.existsByUserAndSubscriber(targetUser, subscriber)) {
            Subscription subscription = new Subscription();
            subscription.setUser(targetUser);
            subscription.setSubscriber(subscriber);
            subscriptionRepository.save(subscription);
        }
    }

    private Friendship getFriendship(User firstUser, User secondUser) {
        User user1 = firstUser.getId() < secondUser.getId() ? firstUser : secondUser;
        User user2 = firstUser.getId() < secondUser.getId() ? secondUser : firstUser;

        return friendshipRepository.findByUser1AndUser2(user1, user2)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(
                "User not found: " + userId));
    }

    private void validateDifferentUsers(Long firstUserId, Long secondUserId) {
        if (firstUserId.equals(secondUserId)) {
            throw new IllegalArgumentException("Users must be different");
        }
    }
}
