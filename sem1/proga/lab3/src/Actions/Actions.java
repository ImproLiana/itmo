package Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Exceptions.DeathException;
import Instruments.Crowd;
import Person.Person;
import Things.*;
import Characters.*;

public class Actions {
    public void StartBall(Hall hall, Portrait portrait, King king, Duchess duchess, Trone trone) throws DeathException{
        Random n = new Random();
        System.out.println("Совсем скоро начало бала.");
        king.present_portrait(duchess, portrait);
        int rand_int = n.nextInt(10);
        int rand_int2 = n.nextInt(30)-15;
        int rend_int3 = n.nextInt(30)-15;
        trone.setGold(rend_int3);
        hall.setQuality(rand_int2);
        ArrayList<Person> guests = new ArrayList<>(Arrays.asList(duchess, king));
        hall.setGuests(guests);
        if (rand_int == 1){
            hall.getOnFire(duchess);
        } else {
            hall.cameIn(duchess);
        }
        try {
            trone.seat(duchess);
        } catch (DeathException e) {
            System.out.println(e.getMessage());
        }
    }

    public void views(Duchess duchess, Princess princess, Crowd[] crowd) throws DeathException{
        for (int i = 0; i < 2; i++){
            for (Crowd pers : crowd) {
                Random n = new Random();
                int rand_int = n.nextInt(4)+1;
                if (rand_int == 1){
                    pers.watching(princess, duchess);
                }
                if (rand_int == 2){
                    pers.watching(duchess, duchess);
                }
                if (rand_int == 3){
                    pers.actBad();
                }
                if(rand_int == 4){
                    pers.actNice();
                }
            }
        }
    }

    public void smtg(Duchess duchess, Princess princess, King king, Vine vine) throws DeathException{
        duchess.smth(vine);
        System.out.print("В это время.. ");
        princess.smth(vine);
        king.smth(vine);
    }

    public void end(Duchess duchess, Princess princess, King king) throws DeathException{
        if (duchess.getReputation() > princess.getReputation() && duchess.getReputation() > king.getReputation()){
            // System.out.println(duchess.getReputation() + " " + princess.getReputation() + " " + king.getReputation());
            if (duchess.getAlive() == false){
                throw new DeathException(duchess);
            }
            if (duchess.getAngry() > duchess.getHappy()){
                System.out.println("Герцогиня остается недовольна.");
                System.out.println("Она убивает падчерицу.");
                princess.setAlive(false);
            }else {
                System.out.println("Герцогиня все таки довольна балом.");
            }
        }
        else if (king.getReputation() > princess.getReputation() && king.getReputation() > duchess.getReputation()){
            if (king.getAlive() == false){
                throw new DeathException(king);
            }
            if (king.getHappy() > king.getAngry()){
                System.out.println("Король сегодня счастлив. Он мирит Грейс и Грудж.");
            } else {
                System.out.println("Король сегодня зол. Он громит зал и выгоняет всех из дворца.");
            }
        }   
        else if (princess.getReputation() > duchess.getReputation() && princess.getReputation() > king.getReputation()){
            if (princess.getAlive() == false){
                throw new DeathException(princess);
            }
            if (princess.getHappy() > princess.getAngry()){
                System.out.println("Принцесса счастива, она сбегает с бала с принцем.");
            } else{
                System.out.println("Принцесса недовольна. Она громит зал и выгоняет всех из дворца.");
            }
        }
        else {
            System.out.println("Все живы здоровы и счастливы. Бал закончился мирно!");
        }
    }
    
}
