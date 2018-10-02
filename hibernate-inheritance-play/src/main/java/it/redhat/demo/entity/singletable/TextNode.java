package it.redhat.demo.entity.singletable;

import javax.persistence.Entity;

// taken from Hibernate OGM project
// https://github.com/hibernate/hibernate-ogm
@Entity
public class TextNode extends Node {

	private String text;

	public TextNode() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
