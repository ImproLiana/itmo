// Король решил задобрить Грудж. Он знал, как ей хочется быть красивой, 
// и заказал для нее портрет, который как могли приукрасили королевские 
// живописцы. Он дал бал в честь герцогини и посадил ее на самое почетное место. 
// Герцогиня сияла от счастья. Ей казалось, что все только и делают, что смотрят на нее.
// На самом деле все приглашенные принцы, короли, герцоги и бароны смотрели на Грейс, 
// которая стояла за спиной своей мачехи. Увидев это, герцогиня решила уничтожить падчерицу.



import Characters.*;
import Instruments.Crowd;
import Things.*;
import Actions.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Создание персонажей
        Princess princess = new Princess("Грейс");
        Duchess duchess = new Duchess("Грудж");
        King king = new King("Альфред");
        Hall hall = new Hall();
        Actions act = new Actions();
        Trone trone = new Trone();
        Portrait portrait = new Portrait();
        Baron baron = new Baron("Нод");
        Duke duke = new Duke("Том");
        Prince prince = new Prince("Николас");
        Vine vine = new Vine(10);
        Crowd[] crowd = {baron, duke, prince};

        act.StartBall(hall, portrait, king, duchess, trone);
        System.out.println();
        act.views(duchess, princess, crowd);
        System.out.println();
        act.smtg(duchess, princess, king, vine);
        System.out.println();
        act.end(duchess, princess, king);
    }
}
