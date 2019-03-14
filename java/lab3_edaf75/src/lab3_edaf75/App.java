package lab3_edaf75;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    Database db = new Database();

    void run() {
        db.openConnection("lab3.sqlite");
        int år = 2016;
        for (MovieInfo movies : db.getMovieInfo("Moonlight", år, (int)111, "tt4975722")) {
            System.out.println(movies.movie_name);
        }
//
//        System.out.println("Most popular colleges/majors");
//        for (ApplicationInfo ai : db.getApplicationInfo()) {
//            System.out.println(ai.count + ":" + ai.college + "/" + ai.major);
//        }
//
//        db.gradeFix("Stanford", 0.04);
    }
}
