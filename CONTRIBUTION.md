# Branching strategy
* Development branch = develop
* Features are developed on `feature/my-feature-name` and then merged to develop when ready
* Release branch = master
* For a new release create a release branch from develop `release/<major>.<minor>` and on there update all the versioning info for 
the release.

# Release Guide
* Create release branch from develop `release/<major>.<minor>`
* Update `deploy.yml` & `src/main/resources/fabric.mod.json` to match the latest release information.
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
    * yarn_mappings
    * loader_version
    * fabric_version
* Update Loom version in build.gradle
```
plugins {
    id 'fabric-loom' version 'x.x-SNAPSHOT'
    ...
}
```
* Delete / clear `remappedSrc` directory
* Update yarn mappings with this command
  `./gradlew migrateMappings --mappings "xxx"`
* In case the used gradle version is not compatible
  * Update the `distributionUrl` in `gradle/wrapper/gradle-wrapper.propperties` 
  * Try migrateMappings again
* Sync gradle projects (refresh icon in gradle area of IDE)
* Update minecraft dependencies in `src/main/resources/fabric.mod.json`
* Update minecraft version in `.github/workflows/deploy.yml`
* Run the gradle task `fabric/genSources` to generate the minecraft sources.
* Run the client with `fabric/runClient`