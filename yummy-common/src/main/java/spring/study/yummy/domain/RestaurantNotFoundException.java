package spring.study.yummy.domain;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(Long id) {
        super("Could not find restaurant id : " + id);
    }
}
