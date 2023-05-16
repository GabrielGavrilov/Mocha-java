import com.gabrielgavrilov.mocha.Mocha;

public class ServerTest {
	public static void main(String[] args) {
		Mocha mocha = new Mocha();

		mocha.set("views", "src/test/java/views/");

		mocha.get("/", (res)-> {
			res.render("index.html");
		});

		mocha.listen(3000, ()-> {
			System.out.println("Server is up and running.");
		});
	}
}
