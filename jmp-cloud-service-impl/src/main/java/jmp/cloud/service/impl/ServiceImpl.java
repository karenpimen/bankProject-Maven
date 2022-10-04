package jmp.cloud.service.impl;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import com.jmp.dto.BankCard;
import com.jmp.dto.Subscription;
import jmp.service.api.*;
import com.jmp.dto.*;

public class ServiceImpl implements Service{
    static final String Url ="jdbc:mysql://localhost:3306/bankdata?useTimezone=true&serverTimezone=UTC";
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void subscribe(BankCard card) {
        try (Connection con = ServiceImpl.getConnection();) {
            LocalDate date = LocalDate.now();
            String cardNum= card.getNumber();

            ps = con.prepareStatement("insert into subscriptions(bank_card, start_date) values(?,?)");
            ps.setString(1, cardNum);
            ps.setDate(2, java.sql.Date.valueOf(date) );
            int result = ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String s) {
        Optional <Subscription> op = Optional.empty() ;

        try(Connection con = ServiceImpl.getConnection();){
            ps = con.prepareStatement("SELECT * FROM subscriptions WHERE bank_card = ?");
            ps.setString(1,s);
            rs = ps.executeQuery();

            if(rs.next()){
                String cardNumber =rs.getString("bank_card");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                Subscription subs = new Subscription(cardNumber, startDate);
                op = Optional.of(subs);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(op.orElseThrow(OptionalException::new));
    }

    @Override
    public List<User> getAllUsers(){
        List <User>users = new ArrayList();
        try(Connection con = ServiceImpl.getConnection();){
            ps = con.prepareStatement("select * from users");
            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("user_id");
                var name =rs.getString("user_name");
                var surname = rs.getString("user_surname");
                var birthday = (rs.getDate("user_birthday").toLocalDate());
                users.add(new User(id,name,surname,birthday));
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return users;
    }
    @Override
    public double getAverageUserAge(){
        return Service.super.getAverageUserAge();
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> p){
        List subs = new ArrayList();
        Subscription sub= getSubscriptionByBankCardNumber("99081179").get();
        if(p.test(sub) )
            subs.add(sub);

        return subs;
    }

    public static Connection getConnection (){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Url, "root", "root");
        }catch (SQLException e){
            System.err.println( e.getMessage());
        }
        return connection;
    }
}
