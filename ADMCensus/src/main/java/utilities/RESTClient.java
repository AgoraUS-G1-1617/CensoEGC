package utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.web.client.RestTemplate;
import domain.User;
import security.UserAccount;
public class RESTClient {

	/***
	 * MÃ©todo que lee un Json de autenticaciÃ³n y devuelve un map con los
	 * username de todos los usuarios registrados en el sistema como clave y sus
	 * emails como valor para que el admin del censo pueda aÃ±adir usuarios y se
	 * les notifique mediante email.
	 */

	public static Map<String, String> getMapUSernameAndEmailByJsonAutentication() {

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("https://autha.agoraus1.egc.duckdns.org/api/index.php?method=getUsers", String.class);
//		System.out.println(result);
		String[] lista = result.split("},");
//		System.out.println(lista);

		Map<String, String> mapUsernamesEmails = new HashMap<String, String>();
		String username = null;
		String email;
		for (@SuppressWarnings("unused")String usuario : lista) {

			String[] lista2 = result.split(",");
			for (String campo : lista2) {
				String[] auxList = campo.split(":");
				if (campo.contains("username")) {
					username = auxList[1];
					username = username.replaceAll("\"", "");
					mapUsernamesEmails.put(username, null);

				}
				if (campo.contains("email")) {
					email = auxList[1];
					email = email.replaceAll("\"", "");
					mapUsernamesEmails.put(username, email);
				}

			}

		}

		return mapUsernamesEmails;
	}

	/***
	 * MÃ©todo que lee nos devuelve el Json con la informaciÃ³n de un usuario en
	 * concreto pasÃ¡ndole un username, el Json obtenido de autenticaciÃ³n se
	 * leerÃ¡ para formar un tipo User que serÃ¡ lo que se devuelva.
	 */

	public static User getCertainUserByJsonAuthentication(String username) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject("https://autha.agoraus1.egc.duckdns.org/api/index.php?method=getUser&user=" + username,
				String.class);
//		System.out.println(result);
		String[] lista = result.split(",");
		User user = new User();
		UserAccount userAccount=new UserAccount();
		for (String field : lista) {
			if (field.contains("username")) {
				String[] auxList = field.split(":");
				String usernameUser = auxList[1];
				usernameUser = usernameUser.replaceAll("\"", "");
				userAccount.setUsername(usernameUser);
			}
			if (field.contains("password")) {
				String[] auxList = field.split(":");
				String password = auxList[1];
				password = password.replaceAll("\"", "");
				userAccount.setPassword(password);
			}
			if (field.contains("email")) {
				String[] auxList = field.split(":");
				String email = auxList[1];
				email = email.replaceAll("\"", "");
				user.setEmail(email);
			}
			if (field.contains("genre")) {
				String[] auxList = field.split(":");
				String genre = auxList[1];
				genre = genre.replaceAll("\"", "");
				user.setGenre(genre);
			}
			if (field.contains("autonomous_community")) {
				String[] auxList = field.split(":");
				String autonomousCommunity = auxList[1];
				autonomousCommunity = autonomousCommunity.replaceAll("\"", "");
				user.setAutonomousCommunity(autonomousCommunity);
			}
			if (field.contains("age")) {
				String[] auxList = field.split(":");
				String age = auxList[1];
				String[] age_list = age.split("}");
				String finalAge = age_list[0];
				String[] s=finalAge.split("\"");
				for(String x:s){
					System.out.println(x);
				}
				int ageConverted = Integer.parseInt(s[1]);
				user.setAge(ageConverted);
			}
		}
//		System.out.println(user);
		user.setUserAccount(userAccount);
		return user;

	}
	
		public static void main(String[] args) throws IOException{
		Map<String, String> usernamesAndEmails = getMapUSernameAndEmailByJsonAutentication();
		System.out.println(usernamesAndEmails);
		Set<String> aux= usernamesAndEmails.keySet();
		System.out.println(aux);
		for(String s:aux){
			System.out.println(s);
			User user =getCertainUserByJsonAuthentication(s);
			System.out.println(user.getUserAccount().getUsername());
			System.out.println(user.getUserAccount().getPassword());
			System.out.println(user.getEmail());
			System.out.println(user.getGenre());
			System.out.println(user.getAutonomousCommunity());
			System.out.println(user.getAge()+"\n"+"////////////////////////////////"+"\n");
		}
	}
}