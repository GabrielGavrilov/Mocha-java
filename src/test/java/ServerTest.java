import com.gabrielgavrilov.mocha.Mocha;

public class ServerTest {
	public static void main(String[] args) {
		Mocha mocha = new Mocha();

		// Sets all the Mocha server settings.
		mocha.set("views", "src/test/java/views/");
		mocha.set("public", "src/test/java/content/");

		// Creates a get request for the index.
		mocha.get("/", (response)-> {
			response.render("index.html");
		});

		// Starts the mocha server.
		mocha.listen(3000, ()-> {
			System.out.println("Server is up and running.");
		});
	}
}
