package org.netchat.network.common;

public class MessageUtil {
    public static final String LOGIN_MSG = "login";
    public static final String MESSAGE_MSG = "message";
    public static final String ERROR_MSG = "error";
    public static final String NONE_MSG = "none";

    public static String createMessage(Message message) {
        String msg = null;
        String text = null;
        if (message == null) {
            msg = NONE_MSG;
        } else {
            msg = message.getMsg();
            text = message.getText();
        }
        return String.format("%s=[%s]", msg, text);
    }

    public static Message readMessage(String s) {
        Message res = new Message();
        res.setMsg(NONE_MSG);
        if (s != null && !s.isEmpty()) {
            int i = s.indexOf('=');
            int j = s.indexOf('[');
            int k = s.lastIndexOf(']');
            if (i > 0) {
                String s1 = s.substring(0, i);
                switch (s1) {
                    case LOGIN_MSG:
                        res.setMsg(LOGIN_MSG);
                        break;
                    case MESSAGE_MSG:
                        res.setMsg(MESSAGE_MSG);
                        break;
                    case ERROR_MSG:
                        res.setMsg(ERROR_MSG);
                        break;
                }
            }
            if (j > 0 && k > 0) {
                res.setText(s.substring(j + 1, k));
            }
        }
        return res;
    }
}
