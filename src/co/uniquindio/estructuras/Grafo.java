package co.uniquindio.estructuras;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Una implementaci�n de gr�fico simple no dirigida y no ponderada.
 *
 * @param <T> El tipo que se usar�a como v�rtice.
 */
public class Grafo<T> {

	String nombreRed;
	private int contador = 0;
	final private HashMap<T, Set<T>> adjacencyList;

	/**
	 * Crear objeto del grafo.
	 */
	public Grafo() {

		this.adjacencyList = new HashMap<>();
		this.nombreRed = new String();
		this.nombreRed = "Red de Vendedores";
	}

	/**
	 * A�adir vertice (Nodo) al grafo. v
	 * 
	 * @param v The vertex object.
	 */
	public void addVertex(T v) {
		if (this.adjacencyList.containsKey(v)) {
			throw new IllegalArgumentException("Usuario ya existente.");
		}
		if (contador < 10) {
			this.adjacencyList.put(v, new HashSet<T>());
			contador++;
		} else {
			String tilte = "Atencion";
			String message = "Ya se encuentran registrados 10 Vendedores!";
			tray.notification.TrayNotification tray = new TrayNotification();
			AnimationType type = AnimationType.POPUP;
			tray.setAnimationType(type);
			tray.setTitle(tilte);
			tray.setMessage(message);
			tray.setNotificationType(NotificationType.NOTICE);
			tray.showAndDismiss(Duration.millis(3000));
		}
	}

	/**
	 * Remover el vertice del grafo
	 * 
	 * @param v The vertex that will be removed.
	 */
	public void removeVertex(T v) {
		if (!this.adjacencyList.containsKey(v)) {
			throw new IllegalArgumentException("El Usuario no existe.");
		}

		this.adjacencyList.remove(v);

		for (T u : this.getAllVertices()) {
			this.adjacencyList.get(u).remove(v);
		}
	}

	/**
	 * Agregar nueva arista entre v�rtices. Agregar un nueva arista de u a v agregar
	 * autom�ticamente un nuevo borde de v a u ya que el gr�fico no est� dirigido.
	 * 
	 * @param v Start vertex.
	 * @param u Destination vertex.
	 */
	public void addEdge(T v, T u) {
		if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
			throw new IllegalArgumentException("Arista ya declarada!");
		}

		this.adjacencyList.get(v).add(u);
		this.adjacencyList.get(u).add(v);

	}

	/**
	 * Elimina el borde entre el v�rtice. Quitar el borde de u a v Elimina
	 * autom�ticamente el borde de v a u ya que el gr�fico no est� dirigido.
	 * 
	 * @param v Start vertex.
	 * @param u Destination vertex.
	 */
	public void removeEdge(T v, T u) {
		if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
			throw new IllegalArgumentException("No existe una Arista!");
		}

		this.adjacencyList.get(v).remove(u);
		this.adjacencyList.get(u).remove(v);
	}

	/**
	 * Verifique la adyacencia entre 2 v�rtices en el gr�fico.
	 *
	 * @param v Inicio del v�rtice.
	 * @param u V�rtice de destino.
	 * @return <tt> true </tt> si los v�rtices v y u est�n conectados.
	 */
	public boolean isAdjacent(T v, T u) {
		return this.adjacencyList.get(v).contains(u);
	}

	/**
	 * Obtener v�rtices conectados de un v�rtice.
	 *
	 * @param v The vertex.
	 * @return An iterable for connected vertices.
	 */
	public Iterable<T> getNeighbors(T v) {
		return this.adjacencyList.get(v);
	}

	/**
	 * Obtenga todos los v�rtices del gr�fico.
	 *
	 * @return Iterable para todos los v�rtices del gr�fico.
	 */
	public Iterable<T> getAllVertices() {
		return this.adjacencyList.keySet();
	}

}