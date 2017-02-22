# GHUpdater
###### A utility library for updating your app through GitHub Releases
[![](https://jitpack.io/v/HellPie/dev.hellpie.libs.piemissions.svg)](https://jitpack.io/#HellPie/dev.hellpie.libs.ghupdater)

## Add it to your project

In your project root open "build.gradle" and to the "repositories" block add:

	maven { url "https://jitpack.io" }

So that your block should look roughly like this:

	repositories {
		jcenter()
		maven { url "https://jitpack.io" }
	}

Then, in your module "build.gradle" add this into the dependencies block:

	compile 'com.github.HellPie:dev.hellpie.libs.ghupdater:1.0.0'
