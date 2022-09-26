package jmp.service.api;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.*;
import java.util.function.*;

import com.jmp.dto.*;

public interface Service {

    void subscribe(BankCard card);
    Optional<Subscription> getSubscriptionByBankCardNumber(String s);
    List<User> getAllUsers();

    //add default method which uses getAllUsers. Use LocalDate.now()
    //double getAverageUsersAge();
    default double getAverageUserAge(){
        List users = getAllUsers();
        List<Double> ages = new ArrayList<>();

        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            User u = it.next();
            LocalDate birthday = u.getBirthday();
            LocalDate today = LocalDate.now();
            double age = ChronoUnit.YEARS.between(birthday,today);
            ages.add(age);
        }
        //double average = ages.stream().mapToLong(i -> i.longValue() ).average().orElse(0);
        double average = ages.stream().mapToLong(Double ::longValue ).average().orElse(0);
        return average;
    }

    //add static method static method to check if user is over 18 years old
    static boolean isPayableUser(User user){
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) >= 18;
    }

    //add a new method, demonstrate this method into application module
    //getAllSubscriptionByCondition(Predicate<Subscription>)
    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> p);


}
