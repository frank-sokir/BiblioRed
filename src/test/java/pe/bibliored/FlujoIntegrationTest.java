package pe.bibliored;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.bibliored.dao.*;
import pe.bibliored.service.*;

@SpringBootTest(
    properties = {
      "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
      "spring.jpa.hibernate.ddl-auto=create-drop"
    })
@org.springframework.test.context.ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class FlujoIntegrationTest {
  @Autowired PrestamoService servicio;
  @Autowired EjemplarDao ejemplares;
  @Autowired PrestamoDao prestamos;
  @Autowired MultaDao multas;
  @Autowired MockMvc mvc;

  @Test
  void flujoCompleto() {
    var p = servicio.solicitar(3L, 1L, LocalDate.now());
    assertEquals("RESERVADO", ejemplares.findById(1L).orElseThrow().estado);
    servicio.entregar(p.id);
    assertEquals(LocalDate.now().plusDays(14), p.fechaLimite);
    servicio.devolver(p.id, "Correcto");
    assertEquals("DISPONIBLE", ejemplares.findById(1L).orElseThrow().estado);
    assertEquals("DEVUELTO", p.estado);
  }

  @Test
  void rechazaDobleReserva() {
    servicio.solicitar(3L, 1L, LocalDate.now());
    assertThrows(IllegalArgumentException.class, () -> servicio.solicitar(3L, 1L, LocalDate.now()));
  }

  @Test
  void rechazaFechaPasada() {
    assertThrows(
        IllegalArgumentException.class,
        () -> servicio.solicitar(3L, 1L, LocalDate.now().minusDays(1)));
  }

  @Test
  void impideDobleDevolucion() {
    var p = servicio.solicitar(3L, 1L, LocalDate.now());
    servicio.entregar(p.id);
    servicio.devolver(p.id, "");
    assertThrows(IllegalArgumentException.class, () -> servicio.devolver(p.id, ""));
  }

  @Test
  void generaMultaYBloqueaNuevaSolicitud() {
    var p = servicio.solicitar(3L, 1L, LocalDate.now());
    servicio.entregar(p.id);
    p.fechaLimite = LocalDate.now().minusDays(3);
    prestamos.save(p);
    servicio.devolver(p.id, "");
    assertEquals(0, new java.math.BigDecimal("3").compareTo(multas.findAll().get(0).monto));
    assertThrows(IllegalArgumentException.class, () -> servicio.solicitar(3L, 2L, LocalDate.now()));
  }

  @Test
  void rechazaSolicitudConPrestamoVencido() {
    var p = servicio.solicitar(3L, 1L, LocalDate.now());
    servicio.entregar(p.id);
    p.fechaLimite = LocalDate.now().minusDays(1);
    prestamos.save(p);
    assertThrows(IllegalArgumentException.class, () -> servicio.solicitar(3L, 2L, LocalDate.now()));
  }

  @Test
  void requiereSesion() throws Exception {
    mvc.perform(get("/api/catalogo")).andExpect(status().isUnauthorized());
  }

  @Test
  void lectorNoAdmin() throws Exception {
    mvc.perform(get("/api/admin/usuarios").with(user("lector@bibliored.local").roles("USUARIO")))
        .andExpect(status().isForbidden());
  }

  @Test
  void exigeCsrf() throws Exception {
    mvc.perform(
            post("/api/prestamos")
                .with(user("lector@bibliored.local").roles("USUARIO"))
                .contentType("application/json")
                .content("{\"ejemplarId\":1,\"fechaRecojo\":\"2026-10-27\"}"))
        .andExpect(status().isForbidden());
  }

  @Test
  void ocultaHash() throws Exception {
    mvc.perform(get("/api/yo").with(user("lector@bibliored.local").roles("USUARIO")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.passwordHash").doesNotExist());
  }

  @Test
  void catalogoBuscaPorCodigo() throws Exception {
    mvc.perform(
            get("/api/catalogo?q=LIB-001").with(user("lector@bibliored.local").roles("USUARIO")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void limitaTresSolicitudes() {
    servicio.solicitar(3L, 1L, LocalDate.now());
    servicio.solicitar(3L, 2L, LocalDate.now());
    servicio.solicitar(3L, 3L, LocalDate.now());
    assertThrows(IllegalArgumentException.class, () -> servicio.solicitar(3L, 4L, LocalDate.now()));
  }

  @Test
  void lectorSoloVeSusPrestamos() throws Exception {
    servicio.solicitar(1L, 1L, LocalDate.now());
    mvc.perform(get("/api/prestamos").with(user("lector@bibliored.local").roles("USUARIO")))
        .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void registraHashSeguro() throws Exception {
    mvc.perform(
            post("/api/registro")
                .with(csrf())
                .contentType("application/json")
                .content(
                    "{\"nombre\":\"Lector"
                        + " Prueba\",\"dni\":\"12345678\",\"correo\":\"prueba@example.test\",\"password\":\"ClavePrueba2026!\",\"bibliotecaId\":1}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.passwordHash").doesNotExist());
  }

  @Test
  void exportaInventarioExcel() throws Exception {
    var r =
        mvc.perform(
                get("/api/personal/reportes/inventario.xlsx")
                    .with(user("personal@bibliored.local").roles("PERSONAL")))
            .andExpect(status().isOk())
            .andReturn();
    try (var wb =
        new org.apache.poi.xssf.usermodel.XSSFWorkbook(
            new java.io.ByteArrayInputStream(r.getResponse().getContentAsByteArray()))) {
      assertEquals(19, wb.getSheetAt(0).getPhysicalNumberOfRows());
      assertEquals("Código", wb.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
    }
  }
}
