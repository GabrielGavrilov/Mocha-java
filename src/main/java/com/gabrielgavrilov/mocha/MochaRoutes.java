package com.gabrielgavrilov.mocha;

import java.util.function.Consumer;

public class MochaRoutes {

	/**
	 * Creates a GET request. Stores the directory and callbacks, and gets called when
	 * appropriate by the MochaClient class.
	 * @param directory Directory for the GET request.
	 * @param callback Runnable callback for the GET request.
	 */
	public void get(String directory, Consumer<MochaResponse> callback) {
		MochaServerAttributes.DIRECTORIES.add(directory);
		MochaServerAttributes.DIRECTORY_CALLBACKS.add(callback);
	}

	// TODO: Add a POST request

}
