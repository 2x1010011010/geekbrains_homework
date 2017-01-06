package org.netchat.network.common;

public class Message {
    private String msg;
    private String text;

    public Message() {
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("Message{msg=[%s] text=[%s]}", this.msg, this.text);
    }
}
