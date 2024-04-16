package co.edu.uniquindio.storify.estructurasDeDatos.nodo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("all")
@Data
@EqualsAndHashCode
@ToString
public class Node<T>{

    private T data;
    private Node<T> nextNode;
    private Node<T> prevNode;

    public Node(T data) {
        this.data = data;
        this.nextNode = null;
        this.prevNode = null;
    }
}
