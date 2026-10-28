package pe.bibliored.service;
import pe.bibliored.dao.*;import pe.bibliored.model.*;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.slf4j.*;import java.time.*;
/** Coordina transacciones de circulación sin exponer reglas al controlador. */
@Service public class PrestamoService {
private final PrestamoDao prestamos;private final EjemplarDao ejemplares;private final UsuarioDao usuarios;private final DevolucionDao devoluciones;private final MultaDao multas;private final PoliticaMulta politica;private static final Logger log=LoggerFactory.getLogger(PrestamoService.class);
public PrestamoService(PrestamoDao p,EjemplarDao e,UsuarioDao u,DevolucionDao d,MultaDao m,PoliticaMulta politica){prestamos=p;ejemplares=e;usuarios=u;devoluciones=d;multas=m;this.politica=politica;}
/** Reserva un ejemplar disponible y registra una solicitud. */
@Transactional public Prestamo solicitar(Long usuarioId,Long ejemplarId,LocalDate recojo){
if(recojo==null||recojo.isBefore(LocalDate.now())||recojo.isAfter(LocalDate.now().plusDays(7)))throw new IllegalArgumentException("El recojo debe ser entre hoy y los próximos siete días");
var u=usuarios.findById(usuarioId).orElseThrow(()->new IllegalArgumentException("Usuario inexistente"));if(!u.activo)throw new IllegalArgumentException("Usuario inactivo");
boolean deuda=multas.findAll().stream().anyMatch(m->m.estado.equals("PENDIENTE")&&prestamos.findById(m.prestamoId).map(p->p.usuarioId.equals(usuarioId)).orElse(false));if(deuda)throw new IllegalArgumentException("Regulariza las multas pendientes antes de solicitar");
if(prestamos.findAll().stream().filter(p->p.usuarioId.equals(usuarioId)&&!p.estado.equals("DEVUELTO")).count()>=3)throw new IllegalArgumentException("Límite de tres préstamos o solicitudes");
var e=ejemplares.bloquear(ejemplarId).orElseThrow(()->new IllegalArgumentException("Ejemplar inexistente"));if(!e.estado.equals("DISPONIBLE"))throw new IllegalArgumentException("El ejemplar ya no está disponible");
var p=new Prestamo();p.usuarioId=usuarioId;p.ejemplarId=ejemplarId;p.fechaSolicitud=LocalDate.now();p.fechaRecojo=recojo;e.estado="RESERVADO";ejemplares.save(e);prestamos.save(p);log.info("Solicitud registrada id={} ejemplar={}",p.id,e.id);return p;}
/** El personal confirma la entrega física e inicia el plazo de 14 días. */
@Transactional public Prestamo entregar(Long id){var p=prestamos.findById(id).orElseThrow(()->new IllegalArgumentException("Préstamo inexistente"));var e=ejemplares.bloquear(p.ejemplarId).orElseThrow();if(!p.estado.equals("SOLICITADO"))throw new IllegalArgumentException("La solicitud ya fue atendida");p.estado="ACTIVO";p.fechaPrestamo=LocalDate.now();p.fechaLimite=p.fechaPrestamo.plusDays(14);e.estado="PRESTADO";ejemplares.save(e);return prestamos.save(p);}
/** Registra una devolución única y genera multa cuando hay retraso. */
@Transactional public Prestamo devolver(Long id,String observacion){var p=prestamos.findById(id).orElseThrow(()->new IllegalArgumentException("Préstamo inexistente"));var e=ejemplares.bloquear(p.ejemplarId).orElseThrow();if(!p.estado.equals("ACTIVO"))throw new IllegalArgumentException("Solo se devuelve un préstamo activo");var d=new Devolucion();d.prestamoId=p.id;d.fechaDevolucion=LocalDate.now();d.observacion=observacion;devoluciones.save(d);var importe=politica.calcular(p.fechaLimite,d.fechaDevolucion);if(importe.signum()>0){var m=new Multa();m.prestamoId=p.id;m.monto=importe;m.motivo="Retraso en devolución";multas.save(m);}p.estado="DEVUELTO";e.estado="DISPONIBLE";ejemplares.save(e);log.info("Devolución registrada préstamo={}",p.id);return prestamos.save(p);}
}
