package frsf.isi.dam.tp.reclamosonline.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Reclamo implements Serializable {

    private Integer id;
    private Integer telefono;
    private String nombre;
    private String correoelectronico;
    private double latitud;
    private double longitud;
    private Estado estado;
    private TipoReclamo tipo;

    public Reclamo() {
    }

    public Reclamo(Integer id, String nombre, String estado, String tipo, Double latitud, Double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = Estado.valueOf(estado);
        this.tipo= TipoReclamo.valueOf(tipo);
    }

    public TipoReclamo getTipo() {
        return tipo;
    }

    public void setTipo(TipoReclamo tipo) {
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }


    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamo reclamo = (Reclamo) o;
        return Objects.equals(id, reclamo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", estado=" + estado +
                ", tipo=" + tipo +
                '}';
    }
}
