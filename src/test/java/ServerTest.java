import com.gabrielgavrilov.mocha.Mocha;
import com.gabrielgavrilov.mocha.MochaRoutes;

public class ServerTest extends Mocha {
	public static void main(String[] args) {
		set("views", "src/test/java/views/");
		set("static", "src/test/java/content/");

		get("/", (response)-> {
			response.render("index.html");
		});

		listen(3000, ()-> {
			System.out.println("Server is up and running.");
		});
	}
}
