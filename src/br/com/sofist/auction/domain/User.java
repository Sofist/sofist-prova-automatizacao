package br.com.sofist.auction.domain;

public class User {

	private int id;
	private String name;
    private String email;

	public User(String nome) {
		this(0, nome);
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
