package domain;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Utilizator(String firstName, String lastName,String email, String password) {;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public void set(Entity other) {
        this.email = ((Utilizator) other).email;
        this.firstName = ((Utilizator) other).firstName;
        this.lastName = ((Utilizator) other).lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String printString() {
        return "id: " + super.getId() + " , " + "firstName: " + firstName + " , " + "lastName: " + lastName;
    }
}