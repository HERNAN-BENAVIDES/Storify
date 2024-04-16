package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@SuppressWarnings("all")
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Artista implements Serializable, Comparable<Artista> {

    private String codigo;
    private String nombre;
    private String nacionalidad;
    private Boolean grupo;
    private ListaEnlazadaDoble<Cancion> canciones;

    public Artista(String codigo, String nombre, String nacionalidad, Boolean grupo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.grupo = grupo;
        this.canciones = new ListaEnlazadaDoble<>();
    }

    @Override
    public String toString() {
        return "Artista{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", grupo=" + grupo +
                '}';
    }

    @Override
    public int compareTo(Artista o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
