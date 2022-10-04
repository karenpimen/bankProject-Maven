package com.bank.application;

import java.time.*;
import java.util.*;
import java.sql.*;
import java.util.function.Consumer;

import com.jmp.dto.*;
import jmp.bank.api.*;
import jmp.service.api.*;
import jmp.cloud.bank.impl.*;
import jmp.cloud.service.impl.ServiceImpl;


public class Application {

    public static void main(String[] args) {

        Bank bank = new BankImpl();
        Service service = new ServiceImpl();

        //Service getAllUsers()
        List<User> users = service.getAllUsers();
        List<BankCard> cards = new ArrayList<>();
        List<Subscription> subscriptions = new ArrayList<>();
        List<Subscription> subscriptionsByCond = new ArrayList<>();
        users = Collections.unmodifiableList(users);

        //Display users
        Iterator<User> itUsers = users.iterator();
        System.out.println("USERS ");
        while (itUsers.hasNext()) {
            User u = itUsers.next();
            System.out.println(u.getName());
            System.out.println(u.getSurname());
            System.out.println(u.getBirthday() + "\n");
        }

        //1.Bank createBankCard()
        users = service.getAllUsers();
        itUsers = users.iterator();

        BankCardType type = BankCardType.CREDIT;
        while (itUsers.hasNext()) {
            cards.add(bank.createBankCard(itUsers.next(), type));

            if (type == BankCardType.CREDIT) type = BankCardType.DEBIT;
            else type = BankCardType.CREDIT;
        }

        //Display cards with username and card type
        System.out.println("CARDS");
        Iterator<BankCard> itCards = cards.iterator();
        while (itCards.hasNext()) {
            BankCard card = itCards.next();
            System.out.println(card.getNumber() + " " + card.getUser().getName() + " " + card.getClass().getName());
        }

        //1. Service subscribe()
        //Create subscriptions for all the cards I have
        cards.forEach(service :: subscribe);

        //2.Service getSubscriptionBYBankCardNUmber()
        System.out.println("\n"+"SUBSCRIPTIONS");
        Optional<Subscription> op= Optional.empty();
        for(BankCard card : cards){
            String s= card.getNumber();
            op = service.getSubscriptionByBankCardNumber(s);
            subscriptions.add( op.get());
            System.out.println(op.isPresent());
        }

        //Call with a no existing card number
        //System.out.println(op = service.getSubscriptionByBankCardNumber("234"));

        //4. Service default method getAverageUserAge()
        System.out.println(service.getAverageUserAge());

        //5. Service static method isPayableUser
        users.forEach(u ->
                System.out.println(u.getName()+" is payable user: "+ Service.isPayableUser(u)));

        //6. Service getAllSubscriptionsbyCondition
        subscriptionsByCond = service.getAllSubscriptionsByCondition(s ->s.getStartDate().compareTo(LocalDate.now()) != 0);
        for(Subscription s : subscriptionsByCond){
            System.out.println("Subscription by condition"+ s.getBankcard()+" Date: "+ s.getStartDate() );
        }

    }
}
