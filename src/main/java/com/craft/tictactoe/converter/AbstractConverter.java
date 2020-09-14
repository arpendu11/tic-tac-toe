package com.craft.tictactoe.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

	public List<T> convert(Collection<S> sources) {
		return sources.stream()
                .map(this::convert)
                .collect(Collectors.toList());
	}

}
