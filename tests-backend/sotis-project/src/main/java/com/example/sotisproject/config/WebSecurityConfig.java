package com.example.sotisproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.sotisproject.security.authority.JWToken;
import com.example.sotisproject.security.authority.RestAuthenticationEntryPoint;
import com.example.sotisproject.security.authority.TokenAuthenticationFilter;
import com.example.sotisproject.service.UserService;

@Configuration
// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
	@Autowired
	private UserService userService;

	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	// Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo nacin utvrdjivanja korisnika pri autentifikaciji
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			// Definisemo uputstva AuthenticationManager-u:
		
			// 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
			// prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
			.userDetailsService(userService) 
			
			// 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu 
			// da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
			.passwordEncoder(passwordEncoder);
	}

	// Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
	@Autowired
	private JWToken jWToken;

	// Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
			// ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru 
			// ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

			// svim korisnicima dopusti da pristupe sledecim putanjama:
			.authorizeRequests().antMatchers("/auth/**").permitAll()		// /auth/**
				.antMatchers("/tests/**").permitAll()
				.antMatchers("/user/**").permitAll()
				.antMatchers("/questions/**").permitAll()
			// ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
			// koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
			// samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin: 
			// .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
							       
			// za svaki drugi zahtev korisnik mora biti autentifikovan
			.anyRequest().authenticated().and()
			
			// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
			.cors().and()

			// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
			.addFilterBefore(new TokenAuthenticationFilter(jWToken, userService), BasicAuthenticationFilter.class);
		
		// zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
		http.csrf().disable();
	}

	// Definisanje konfiguracije koja utice na generalnu bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) {
		// Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
		// Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
		
		// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");

		web.ignoring().antMatchers(HttpMethod.GET, "/tests/");
		web.ignoring().antMatchers(HttpMethod.GET, "/tests/{id}");
		web.ignoring().antMatchers(HttpMethod.GET, "/tests/published");
		web.ignoring().antMatchers(HttpMethod.GET, "/tests/{id}/published");
		web.ignoring().antMatchers(HttpMethod.GET, "/tests/unpublished");
		web.ignoring().antMatchers(HttpMethod.POST, "/tests/add");
		web.ignoring().antMatchers(HttpMethod.POST, "/tests/submit");

		web.ignoring().antMatchers(HttpMethod.GET, "/concepts/");
		web.ignoring().antMatchers(HttpMethod.GET, "/concepts/{testId}");
		web.ignoring().antMatchers(HttpMethod.POST, "/concepts/add");
		web.ignoring().antMatchers(HttpMethod.POST, "/concepts/delete");

		web.ignoring().antMatchers(HttpMethod.GET, "/relations/");
		web.ignoring().antMatchers(HttpMethod.POST, "/relations/update");

		web.ignoring().antMatchers(HttpMethod.GET, "/realRelations/{testId}");
		web.ignoring().antMatchers(HttpMethod.POST, "/realRelations/{testId}/create");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/{id}/answers");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/tests");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/test");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/tests/{testId}");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/{userId}/tests/{testId}");
		web.ignoring().antMatchers(HttpMethod.GET, "/students/{id}");

		web.ignoring().antMatchers(HttpMethod.GET, "/studentAnswers/allResults");
		web.ignoring().antMatchers(HttpMethod.GET, "/studentAnswers/allResults{testId}");

		web.ignoring().antMatchers(HttpMethod.GET, "/realKS/{testId}");
		web.ignoring().antMatchers(HttpMethod.POST, "/realKS/{testId}/create");

		web.ignoring().antMatchers(HttpMethod.GET, "/testPublications/{testId}");
		web.ignoring().antMatchers(HttpMethod.POST, "/testPublications/{publicationId}/republish");
		web.ignoring().antMatchers(HttpMethod.POST, "/testPublications/publishNewVersion");


		web.ignoring().antMatchers(HttpMethod.GET, "/sparql/{conceptName}/directNextConcepts");
		web.ignoring().antMatchers(HttpMethod.GET, "/sparql/{conceptName}/allPreviousConcepts");
		web.ignoring().antMatchers(HttpMethod.GET, "/sparql/{conceptName}/solvableTests");
		web.ignoring().antMatchers(HttpMethod.POST, "/sparql/studentsForTeam");

		// Ovim smo dozvolili pristup statickim resursima aplikacije
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}
}
