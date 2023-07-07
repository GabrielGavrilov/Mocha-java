package com.gabrielgavrilov.mocha;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MochaRoutes {

	/**
	 * Creates a GET request. Stores the directory and callbacks, and gets called when
	 * appropriate by the MochaClient class.
	 *
	 * @param directory Directory for the GET request.
	 * @param callback Runnable callback for the GET request.
	 */
	public static void get(String directory, Consumer<MochaResponse> callback) {
		MochaServerAttributes.GET_DIRECTORIES.add(directory);
		MochaServerAttributes.GET_DIRECTORY_CALLBACKS.add(callback);
	}

	// TODO: Add a POST request
	public static void post(String directory, BiConsumer<MochaRequest, MochaResponse> callback) {
		MochaServerAttributes.POST_DIRECTORIES.add(directory);
		MochaServerAttributes.POST_DIRECTORY_CALLBACKS.add(callback);
	}

	protected static void renderStaticDirectory(String directory, Consumer<MochaResponse> callback) {
		MochaServerAttributes.STATIC_DIRECTORIES.add(directory);
		MochaServerAttributes.STATIC_DIRECTORY_CALLBACKS.add(callback);
	}

}
