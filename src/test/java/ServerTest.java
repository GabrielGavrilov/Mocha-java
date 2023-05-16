import com.gabrielgavrilov.mocha.Mocha;

public class ServerTest {
	public static void main(String[] args) {
		Mocha mocha = new Mocha();

		mocha.get("/", (res)-> {
			res.send("<h2>Hello, World!</h2>");
		});

		mocha.listen(3000, ()-> {
			System.out.println("Server is up and running.");
		});
	}
}
