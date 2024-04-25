package whereQR.project.service;import com.fasterxml.jackson.core.JsonProcessingException;import lombok.RequiredArgsConstructor;import lombok.extern.slf4j.Slf4j;import org.springframework.messaging.MessagingException;import org.springframework.messaging.simp.SimpMessagingTemplate;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import whereQR.project.entity.Chatroom;import whereQR.project.entity.Member;import whereQR.project.entity.Message;import whereQR.project.entity.dto.message.ResponseMessageDto;import whereQR.project.exception.CustomExceptions.InternalException;import whereQR.project.exception.CustomExceptions.NotFoundException;import whereQR.project.repository.message.MessageRepository;import java.util.List;import java.util.UUID;import java.util.stream.Collectors;@Service@Slf4j@Transactional(readOnly = true)@RequiredArgsConstructorpublic class MessageService {    private final MessageRepository messageRepository;    private final MemberService memberService;    private final SimpMessagingTemplate simpMessagingTemplate;    public Message getMessageById(UUID id){        return messageRepository.findById(id).orElseThrow(() -> new NotFoundException("해당하는 메시지가 존재하지 않습니다.", this.getClass().toString()));    }    /**     * 채팅     */    @Transactional    public Message sendMessage(Chatroom chatroom, Member sender, String content){        // get receiver        UUID receiverId = chatroom.getReceiverBySender(sender);        Member receiver = memberService.getMemberById(receiverId);        // 1. 메시지 생성        Message message = new Message(sender, receiver, chatroom, content);        // 2. 메시지 전송        try{            simpMessagingTemplate.convertAndSend("/subscribe/" + chatroom.id, message.toString());            log.info("sendMessage / convertAndSend success");        } catch (MessagingException exception){            throw new InternalException(exception.getFailedMessage().toString(), this.getClass().toString());        }        // 3. 저장        return messageRepository.save(message);    }    @Transactional    public void readMessage(Member member, Chatroom chatroom, Message message ){        // 현재 사람이 메시지를 받은 입장이면 read처리        if(message.isReceiver(member)){            log.info("is receiver");            message.readMessage();            messageRepository.save(message);        }        try{            simpMessagingTemplate.convertAndSend("/subscribe/" + chatroom.id + "/" + message.id, message.toString());            log.info("readMessage / convertAndSend success");        } catch (MessagingException exception){            throw new InternalException(exception.getFailedMessage().toString(), this.getClass().toString());        }    }    public List<ResponseMessageDto> getMessagesByChatroomId(UUID chatroomId){        return messageRepository.findMessagesByChatroomId(chatroomId).stream().map(Message::toResponseMessageDto).collect(Collectors.toList());    }}