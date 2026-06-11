package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSaveMessageThenFindById() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        Message message = messageRepository.save(TestDataFactory.createMessage(sender, receiver));
        Optional<Message> messageOptional = messageRepository.findById(message.getId());
        assertThat(messageOptional).isPresent();
        assertThat(messageOptional.get().getId()).isEqualTo(message.getId());
    }

    @Test
    void whenFindAllThenReturnAllMessages() {
        User sender = userRepository.save(TestDataFactory.createUser());
        User receiver = userRepository.save(TestDataFactory.createUser());
        Message message1 = messageRepository.save(TestDataFactory.createMessage(sender, receiver));
        Message message2 = messageRepository.save(TestDataFactory.createMessage(sender, receiver));
        List<Message> messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
        assertThat(messages).extracting(Message::getId).contains(message1.getId(), message2.getId());
    }
}