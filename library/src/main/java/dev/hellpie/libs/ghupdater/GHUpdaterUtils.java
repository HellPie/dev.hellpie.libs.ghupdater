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

package dev.hellpie.libs.ghupdater;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dev.hellpie.libs.ghupdater.internal.AssetModel;
import dev.hellpie.libs.ghupdater.internal.ReleaseResponse;

public final class GHUpdaterUtils {

	private final GHConfig config;

	public GHUpdaterUtils(@NonNull GHConfig config) {
		this.config = config;
	}

	@Nullable
	public GHUpdateInfo getLatestVersion() throws ExecutionException, InterruptedException {
		return new AsyncTask<Void, Void, GHUpdateInfo>() {

			@Override
			protected GHUpdateInfo doInBackground(Void... params) {
				try {
					if(!GHUtils.isValidRepo(config.getUserName(), config.getRepoName()))
						return null;
				} catch(IOException e) {
					e.printStackTrace();
				}

				List<ReleaseResponse> releases;
				try {
					releases = GHUtils.getReleases(config.getUserName(), config.getRepoName());

					if(releases.isEmpty()) return null;

					ReleaseResponse latest = null;
					for(ReleaseResponse release : releases) {
						if(!isValidRelease(release) || (latest != null && release.publishedAt.after(latest.publishedAt)))
							continue;

						for(AssetModel asset : release.assets)
							if(isValidAsset(asset))
								return GHUpdateInfo.Builder.fromData(release, asset);
						latest = release;
					}

					return null;
				} catch(IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		}.execute().get();
	}

	private boolean isValidRelease(ReleaseResponse release) {
		return !release.assets.isEmpty()
				&& (config.acceptPrereleases() || !release.prerelease)
				&& release.publishedAt.after(config.getMinimumDate());
	}

	private boolean isValidAsset(AssetModel asset) {
		return config.isValidFile(asset.name) && config.isValidMIMEType(asset.contentType);
	}
}
