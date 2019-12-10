package frsf.isi.dam.tp.reclamosonline.modelo;

import java.util.Objects;

public class Reclamo {
    private Integer id;
    private String nombre;
    private float latitud;
    private float longitud;
    private Estado estado;

    public Reclamo() {
    }

    public Reclamo(Integer id, String nombre, float latitud, float longitud, Estado estado) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
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
}
