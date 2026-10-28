package pe.bibliored.config;

import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.bibliored.dao.*;
import pe.bibliored.model.*;

/** Datos de demostración ficticios, únicamente para el perfil local. */
@Configuration
@Profile("!mysql")
public class DatosDemo {
  @Bean
  CommandLineRunner datos(
      BibliotecaDao bd, UsuarioDao ud, LibroDao ld, EjemplarDao ed, PasswordEncoder encoder) {
    return args -> {
      if (bd.count() > 0) return;
      for (String nombre :
          new String[] {
            "Biblioteca Central Lima", "Biblioteca Los Olivos", "Biblioteca San Miguel"
          }) {
        var b = new Biblioteca();
        b.nombre = nombre;
        b.direccion = "Lima, Perú";
        bd.save(b);
      }
      String[][] cuentas = {
        {"Administrador demo", "admin@bibliored.local", "ADMIN", "00000001"},
        {"Personal demo", "personal@bibliored.local", "PERSONAL", "00000002"},
        {"Lector demo", "lector@bibliored.local", "USUARIO", "00000003"}
      };
      for (var c : cuentas) {
        var u = new Usuario();
        u.nombre = c[0];
        u.correo = c[1];
        u.rol = c[2];
        u.dni = c[3];
        u.bibliotecaId = 1L;
        u.passwordHash = encoder.encode("BiblioRed2026!");
        ud.save(u);
      }
      String[][] obras = {
        {"Don Quijote de la Mancha", "Miguel de Cervantes", "Literatura", "9788420412146"},
        {"La ciudad y los perros", "Mario Vargas Llosa", "Literatura", "9788420471839"},
        {"El principito", "Antoine de Saint-Exupéry", "Narrativa", "9780156012195"},
        {"Cien años de soledad", "Gabriel García Márquez", "Literatura", "9780307474728"},
        {"Historia del Perú", "Jorge Basadre", "Historia", "9786120000010"},
        {"Breve historia del tiempo", "Stephen Hawking", "Ciencia", "9780553380163"}
      };
      int k = 1;
      for (var o : obras) {
        var l = new Libro();
        l.titulo = o[0];
        l.autor = o[1];
        l.categoria = o[2];
        l.isbn = o[3];
        l.editorial = "Catálogo de demostración";
        ld.save(l);
        for (long sede = 1; sede <= 3; sede++) {
          var e = new Ejemplar();
          e.libroId = l.id;
          e.bibliotecaId = sede;
          e.codigo = String.format("LIB-%03d", k++);
          ed.save(e);
        }
      }
    };
  }
}
