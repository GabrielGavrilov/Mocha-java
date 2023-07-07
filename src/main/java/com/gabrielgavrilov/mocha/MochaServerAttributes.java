package com.gabrielgavrilov.mocha;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MochaServerAttributes {

	protected static String VIEWS_DIRECTORY;
	protected static String STATIC_DIRECTORY;

	protected static ArrayList<String> GET_DIRECTORIES = new ArrayList<>();
	protected static ArrayList<Consumer<MochaResponse>> GET_DIRECTORY_CALLBACKS = new ArrayList<>();

	protected static ArrayList<String> POST_DIRECTORIES = new ArrayList<>();
	protected static ArrayList<BiConsumer<MochaRequest, MochaResponse>> POST_DIRECTORY_CALLBACKS = new ArrayList<>();

	protected static ArrayList<String> STATIC_DIRECTORIES = new ArrayList<>();
	protected static ArrayList<Consumer<MochaResponse>> STATIC_DIRECTORY_CALLBACKS = new ArrayList<>();

}
