package co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimple;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.BinaryNode;
import co.edu.uniquindio.storify.exceptions.EmptyNodeException;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("all")
@Data
public class BinaryTree<T extends Comparable<T>> implements Serializable {
    private BinaryNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(T data) {
        this.root = new BinaryNode<>(data);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.getLeftNode()) + size(root.getRightNode());
    }

    private int size(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeftNode()) + size(node.getRightNode());
    }

    public void insert(T data) {
        this.root = insert(this.root, data);
    }

    private BinaryNode<T> insert(BinaryNode<T> node, T data) {
        if (node == null) {
            return new BinaryNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftNode(insert(node.getLeftNode(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightNode(insert(node.getRightNode(), data));
        }
        return node;
    }

    public T find(T data) {
        return (T) find(this.root, data);
    }

    private T find(BinaryNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            return find(node.getLeftNode(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return find(node.getRightNode(), data);
        }
        return node.getData();
    }

    public ListaEnlazadaSimple<T> iterator() {
        ListaEnlazadaSimple<T> list = new ListaEnlazadaSimple<>();
        iterator(this.root, list);
        return list;
    }

    private void iterator(BinaryNode<T> node, ListaEnlazadaSimple<T> list) {
        if (node != null) {
            iterator(node.getLeftNode(), list); // Recorrer el subárbol izquierdo
            list.add(node.getData()); // Agregar el dato del nodo actual a la list
            iterator(node.getRightNode(), list); // Recorrer el subárbol derecho
        }
    }

    public void delete(T data) throws EmptyNodeException {
        if (this.root == null) {
            throw new EmptyNodeException("El árbol está vacío");
        }
        this.root = delete(this.root, data);
    }

    private BinaryNode<T> delete(BinaryNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftNode(delete(node.getLeftNode(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightNode(delete(node.getRightNode(), data));
        } else { // Nodo encontrado
            if (node.getLeftNode() == null) {
                return node.getRightNode(); // Caso sin hijo izquierdo
            } else if (node.getRightNode() == null) {
                return node.getLeftNode(); // Caso sin hijo derecho
            } else {
                // Caso con ambos hijos
                T min = findMin(node.getRightNode());
                node.setData(min);
                node.setRightNode(delete(node.getRightNode(), min));
            }
        }
        return node;
    }

    private T findMin(BinaryNode<T> node) {
        while (node.getLeftNode() != null) {
            node = node.getLeftNode();
        }
        return node.getData();
    }

    // Método para recorrer el árbol en orden (inorden)
    public void inorder() {
        inorder(this.root);
    }

    // Método privado recursivo para recorrer el árbol en orden (inorden)
    private void inorder(BinaryNode<T> node) {
        if (node != null) {
            inorder(node.getLeftNode()); // Recorrer el subárbol izquierdo
            System.out.println(node.getData()); // Visitar el nodo actual
            inorder(node.getRightNode()); // Recorrer el subárbol derecho
        }
    }

}

