package pi4.example.pi4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Pi4Application {

	public static void main(String[] args) {
		SpringApplication.run(Pi4Application.class, args);
                // Mensagem destacada no console com o link
        System.out.println("\n#############################################");
        System.out.println("#                                           #");
        System.out.println("#  O aplicativo está rodando!                #");
        System.out.println("#                                           #");
        System.out.println("#  Acesse: http://localhost:8080/clientes  #");
        System.out.println("#                                           #");
        System.out.println("#############################################\n");
	}

}
