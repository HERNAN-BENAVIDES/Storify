package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import lombok.*;

import java.io.Serializable;


//@SuppressWarnings("all")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Artista implements Serializable, Comparable<Artista> {

    private String codigo;
    private String nombre;
    private String nacionalidad;
    private TipoArtista tipoArtista;
    private ListaEnlazadaDoble<Cancion> canciones;

    public Artista(String codigo, String nombre, String nacionalidad, TipoArtista tipoArtista) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.tipoArtista = tipoArtista;
        this.canciones = new ListaEnlazadaDoble<>();
    }

    /**
     * @Override
     *     public String toString() {
     *         return "Artista{" +
     *                 "codigo='" + codigo + '\'' +
     *                 ", nombre='" + nombre + '\'' +
     *                 ", nacionalidad='" + nacionalidad + '\'' +
     *                 ", grupo=" +  (grupo ? "Si" : "No") +
     *                 ", canciones=" + canciones +
     *                 '}';
     *     }
     */

    /**
     *
     * public static ArtistaBuilder builder() {
     *         return new ArtistaBuilder();
     *     }
     *
     *     public static class ArtistaBuilder {
     *         private String codigo;
     *         private String nombre;
     *         private String nacionalidad;
     *         private TipoArtista tipoArtista;
     *
     *         public ArtistaBuilder codigo(String codigo) {
     *             this.codigo = codigo;
     *             return this;
     *         }
     *
     *         public ArtistaBuilder nombre(String nombre) {
     *             this.nombre = nombre;
     *             return this;
     *         }
     *
     *         public ArtistaBuilder nacionalidad(String nacionalidad) {
     *             this.nacionalidad = nacionalidad;
     *             return this;
     *         }
     *
     *         public ArtistaBuilder tipoArtista(TipoArtista tipoArtista) {
     *             this.tipoArtista = tipoArtista;
     *             return this;
     *         }
     *
     *         public Artista build() {
     *             return new Artista(codigo, nombre, nacionalidad, tipoArtista);
     *         }
     *     }
     * @param o the object to be compared.
     * @return
     */


    @Override
    public int compareTo(Artista o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}