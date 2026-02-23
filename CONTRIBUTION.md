# Branching strategy
* Development branch = develop
* Features are developed on `feature/my-feature-name` and then merged to develop when ready
* Release branch = master
* For a new release create a release branch from develop `release/<major>.<minor>` and on there update all the versioning info for 
the release.

# Release Guide
* Create release branch from develop `release/<major>.<minor>`
* Update `deploy.yml` & `gradle.properties` to match the latest release information.
* Update `CHANGELOG.md` with the latest changes.
* Manually build and test everything!
  * Run `clean` & `build` -> Built mod should be located in `build/libs/itemmagnetmod-x.x.jar`
* Merge release branch to master.
* The release is automatically deployed to modrinth and curseforge. 
* A new release + tag is automatically created in github
  * It could be necessary to manually update the release notes

# Update Guide
* Update gradle.properties in accordance to: https://fabricmc.net/develop/
    * minecraft_version
    * loader_version
    * fabric_version
  * This accordance to the latest versions of the mod for the current game
    * cloth_config_version
    * modmenu_versio
  * And this, if the game uses a newer version of Java
    * java_version
* Update Loom version in build.gradle
```
plugins {
    	id 'net.fabricmc.fabric-loom-remap' version 'x.xx-SNAPSHOT'
    ...
}
```
* Delete / clear `remappedSrc` directory
* In case the used gradle version is not compatible
  * Update the `distributionUrl` in `gradle/wrapper/gradle-wrapper.propperties` 
  * Check here for changes to the gradle version in loom: https://github.com/FabricMC/fabric-loom/releases
  * Try migrateMappings again
* Sync gradle projects (refresh icon in gradle area of IDE)
*  Update minecraft dependencies in `gradle.properties`
* Update minecraft version in `.github/workflows/deploy.yml`
* Run the gradle task `fabric/genSources` to generate the minecraft sources.
* Run the client with `fabric/runClient`
