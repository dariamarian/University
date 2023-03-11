package domain;

import utils.Constants;

import java.time.LocalDateTime;

public class Message extends Entity<Long>{

    private long id_sender;
    private long id_receiver;
    private String messageText;
    private LocalDateTime timeSent;

    public Message(long sender, long receiver, String message)
    {
        this.id_sender=sender;
        this.id_receiver=receiver;
        this.messageText =message;
        this.timeSent=LocalDateTime.now();
    }

    @Override
    public void set(Entity other) {
        id_sender=((Message)other).id_sender;
        id_receiver=((Message)other).id_receiver;
        messageText=((Message)other).messageText;
        timeSent=((Message)other).timeSent;
    }

    public long getIdSender() {
        return id_sender;
    }

    public long getIdReceiver() {
        return id_receiver;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getTimeSentString(){
        return timeSent.format(Constants.FORMATTER_MESSAGE);
    }
    public void setTimeSent(String time){
        timeSent=LocalDateTime.parse(time,Constants.FORMATTER_MESSAGE);
    }
    public LocalDateTime getTimeSent() {
        return timeSent;
    }


}
