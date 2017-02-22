/*
 * Copyright 2017 Diego Rossi (@_HellPie)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.hellpie.libs.ghupdater.internal;

import com.google.gson.annotations.SerializedName;

public final class RepoResponse {

	private static final String ERROR_MESSAGE = "Not Found";

	@SerializedName("message")
	protected String errorMessage;

	public boolean exists() {
		return (errorMessage == null || errorMessage.isEmpty() || errorMessage.equals(ERROR_MESSAGE));
	}
}
