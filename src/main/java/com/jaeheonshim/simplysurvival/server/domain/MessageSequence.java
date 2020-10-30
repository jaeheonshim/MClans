package com.jaeheonshim.simplysurvival.server.domain;

public class MessageSequence {
    private String message;
    private MessageSequence nextMessage;
    private MessageSequence start;

    private MessageSequence(String message, MessageSequence start) {
        this.message = message;
        this.start = start;
    }

    private MessageSequence(String message) {
        this.message = message;
        this.start = this;
    }

    public static MessageSequence start(String message) {
        return new MessageSequence(message);
    }

    public MessageSequence getNext() {
        return nextMessage;
    }

    public MessageSequence addNext(String nextMessage) {
        MessageSequence next = new MessageSequence(nextMessage, this.start);
        this.nextMessage = next;
        return next;
    }

    public MessageSequence getStart() {
        return this.start;
    }

    public String getMessage() {
        return message;
    }
}
