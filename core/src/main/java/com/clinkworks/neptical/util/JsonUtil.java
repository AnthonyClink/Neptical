package com.clinkworks.neptical.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class JsonUtil {
<<<<<<< HEAD:core/src/main/java/com/clinkworks/neptical/util/JsonUtil.java
	
	private static final Gson GSON;
	private static final JsonParser PARSER;
	
	static{
		IntAsBooleanAdapter booleanAdapter = new IntAsBooleanAdapter();
		GSON = new GsonBuilder()
	      .registerTypeAdapter(Integer.class, booleanAdapter)
	      .registerTypeAdapter(int.class, booleanAdapter)
	      .create();
		PARSER = new JsonParser();
	}
	
	private JsonUtil(){
		Common.noOp("This class is designed to be used as a static utility");
	}
	
	public static final JsonElement toJson(Object object){
		return GSON.toJsonTree(object);
	}
	
	public static final JsonElement parseFromFile(String path){
=======

	private static Gson GSON;
	private static final JsonParser PARSER;

	private static final Map<Type, TypeAdapter<?>> ADAPTERS;

	static {
		ADAPTERS = new HashMap<Type, TypeAdapter<?>>();
		IntAsBooleanAdapter booleanAdapter = new IntAsBooleanAdapter();
		ADAPTERS.put(Boolean.class, booleanAdapter);
		ADAPTERS.put(int.class, booleanAdapter);
		GSON = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd")
				.registerTypeAdapter(Integer.class, booleanAdapter)
				.registerTypeAdapter(int.class, booleanAdapter).create();
		PARSER = new JsonParser();
	}

	private JsonUtil() {
		Common.noOp("This class is designed to be used as a static utility");
	}

	/**
	 * This method is untested in multi threaded envroments.
	 * 
	 * This method WILL REPLACE the current Gson singleton, it is expensive, the
	 * need for this method implies a need for a redesign of this utility
	 * 
	 * @param type
	 * @param typeAdapter
	 */
	public static final synchronized void registerTypeAdapter(Type type,
			TypeAdapter<?> typeAdapter) {
		ADAPTERS.put(type, typeAdapter);
		GsonBuilder gsonBuilder = new GsonBuilder();
		for (Map.Entry<Type, TypeAdapter<?>> entry : ADAPTERS.entrySet()) {
			gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}
	
		GSON = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
	}

	/**
	 * This method is untested in multi threaded envroments.
	 * 
	 * This method WILL REPLACE the current Gson singleton, it is expensive, the
	 * need for this method implies a need for a redesign of this utility
	 * 
	 * @param type
	 * @param typeAdapter
	 */
	public static final synchronized void registerTypeAdapters(Map<Type, TypeAdapter<?>> typeAdapters) {
		ADAPTERS.putAll(typeAdapters);
		GsonBuilder gsonBuilder = new GsonBuilder();
		for (Map.Entry<Type, TypeAdapter<?>> entry : ADAPTERS.entrySet()) {
			gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}
		GSON = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
	}

	public static final JsonElement toJson(Object object) {
		return GSON.toJsonTree(object);
	}

	public static final JsonElement parseFromFile(String path) {
>>>>>>> master:src/main/java/com/clinkworks/neptical/util/JsonUtil.java
		return parse(new File(path));
	}

	public static final <T> T toObject(Class<T> type, JsonElement json) {
		return GSON.fromJson(json, type);
	}

	public static final <T> T toObject(Class<T> type, String json) {
		return GSON.fromJson(json, type);
	}

	public static final JsonElement parse(String json) {
		return PARSER.parse(json);
	}

	public static final JsonElement parse(File file) {
		try {
			return parse(new BufferedReader(new FileReader(file)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static final JsonElement parse(InputStream inputStream) {
		return parse(new BufferedReader(new InputStreamReader(inputStream)));
	}

	private static final JsonElement parse(BufferedReader bufferedReader) {

		JsonElement element = null;
		try {
			element = PARSER.parse(bufferedReader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return element;
	}
<<<<<<< HEAD:core/src/main/java/com/clinkworks/neptical/util/JsonUtil.java
	
	public static class IntAsBooleanAdapter extends TypeAdapter<Integer>{
		  
		@Override 
		public void write(JsonWriter out, Integer value) throws IOException {
		    if (value == null) {
		      out.nullValue();
		    } else {
		      out.value(value);
		    }
		  }
		  
		  @Override 
		  public Integer read(JsonReader in) throws IOException {
		    JsonToken peek = in.peek();
		    switch (peek) {
		    case BOOLEAN:
		      return in.nextBoolean() ? 1 : 0;
		    case NULL:
		      in.nextNull();
		      return null;
		    case NUMBER:
		      return in.nextInt();
		    case STRING:
		      return Integer.parseInt(in.nextString());
		    default:
		      throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
		    }
		  }
		}	
=======

	public static class IntAsBooleanAdapter extends TypeAdapter<Integer> {

		@Override
		public void write(JsonWriter out, Integer value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(value);
			}
		}

		@Override
		public Integer read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			switch (peek) {
			case BOOLEAN:
				return in.nextBoolean() ? 1 : 0;
			case NULL:
				in.nextNull();
				return null;
			case NUMBER:
				return in.nextInt();
			case STRING:
				return Integer.parseInt(in.nextString());
			default:
				throw new IllegalStateException(
						"Expected BOOLEAN or NUMBER but was " + peek);
			}
		}
	}

>>>>>>> master:src/main/java/com/clinkworks/neptical/util/JsonUtil.java
}