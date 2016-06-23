package com.movieapp.interfaces;

import com.adventnet.persistence.Row;

public interface RowAdapter <T> {
	public Row asRow(T data);
	public T asBean(Row row);
}
