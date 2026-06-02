package creational;

class User {
    private int age;
    private String name;

    // Telescopic constructor issue -> telescopic means constructor params keeps on increasing with increasing variables
    User(){}

    User(int age){
        this.age = age;
    }

    User(String name){
        this.name = name;
    }

    User(int age, String name){
        this.age = age;
        this.name = name;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public String toString(){
        return "User with age : " + age + " and name : " + name;
    }
}

/*
    Builder has setters with name same as that of variables
    and call for the all arguments constructor when it is time to build
*/
class UserBuilder {
    private int age;
    private String name;

    public UserBuilder age(int age){
        this.age = age;
        return this;
    }

    public UserBuilder name(String name){
        this.name = name;
        return this;
    }

    public User build(){
        return new User(age, name);
    }
}

public class Builder {
    public static void main(String[] args) {
        User user = User.builder()
                    .age(20)
                    .name("Rajat")
                    .build();
        System.out.println(user.toString());
    }
}
