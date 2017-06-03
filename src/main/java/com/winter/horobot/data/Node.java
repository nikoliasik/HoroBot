package com.winter.horobot.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Node<T> {

	private final T data;
	private final List<Node<T>> children;
	private Node<T> parent;

	public Node(T data, List<Node<T>> children) {
		this.data = data;
		this.children = children;
		children.forEach(c -> c.setParent(this));
	}

	public T getData() {
		return data;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	private void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getParent() {
		return parent;
	}

	public <U> U compileTopDown(Function<T, U> converter, BiFunction<U, U, U> concatenator) {
		List<Node<T>> me = new ArrayList<>();
		me.add(this);
		Node<T> current = this;
		while (current.getParent() != null) {
			me.add(current.getParent());
			current = current.getParent();
		}
		U currentU = converter.apply(me.get(0).getData());
		for (int i = 1; i < me.size(); i++) {
			currentU = concatenator.apply(converter.apply(me.get(i).getData()), currentU);
		}
		return currentU;
	}

	public <U> Node<T> traverseThis(Function<Node<T>, U> converter, U toMatch) {
		if (converter.apply(this).equals(toMatch)) {
			return this;
		} else {
			for (Node<T> child : getChildren()) {
				if (child.traverseThis(converter, toMatch).equals(toMatch)) {
					return child;
				}
			}
		}
		return null;
	}

}
