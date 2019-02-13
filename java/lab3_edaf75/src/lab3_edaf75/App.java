
public class App {

    public static void main(String[] args) {
        new App().run();
    }

    Database db = new Database();

    void run() {
        db.openConnection("lab3.sqlite");
        for (MovieInfo student : db.getMovieInfo("Stanford", "CS")) {
            System.out.println(student.name + ": " + student.gpa);
        }

        System.out.println("Most popular colleges/majors");
        for (ApplicationInfo ai : db.getApplicationInfo()) {
            System.out.println(ai.count + ":" + ai.college + "/" + ai.major);
        }

        db.gradeFix("Stanford", 0.04);
    }
}
