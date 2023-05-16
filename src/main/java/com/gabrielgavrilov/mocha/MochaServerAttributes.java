package com.gabrielgavrilov.mocha;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MochaServerAttributes {

	protected static String VIEWS_DIRECTORY;
	protected static String PUBLIC_DIRECTORY;

	protected static ArrayList<String> DIRECTORIES = new ArrayList<>();
	protected static ArrayList<Consumer<MochaResponse>> DIRECTORY_CALLBACKS = new ArrayList<>();
}
