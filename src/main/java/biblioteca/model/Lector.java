package biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_lector")
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, unique = true, length = 12)
    private String dni;

    @Column(length = 80)
    private String correo;

    @Column(nullable = false)
    private Boolean activo = true;

    public Lector() {}

    public Lector(String nombres, String dni, String correo) {
        this.nombres = nombres;
        this.dni = dni;
        this.correo = correo;
        this.activo = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Lector [id=" + id + ", nombres=" + nombres + ", dni=" + dni
                + ", correo=" + correo + ", activo=" + activo + "]";
    }
}
