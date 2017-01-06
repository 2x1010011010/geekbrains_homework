package org.netchat.network.server.logic.main;

public abstract class User {
    protected long id;
    protected String login;

    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (user.getLogin() == null || this.login == null) return false;
        return login.equals(user.login);
    }
}
