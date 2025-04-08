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

* Update yarn mappings with this command
  `./gradlew migrateMappings --mappings "xxx"`

* Update Loom version in build.gradle
```
plugins {
    id 'fabric-loom' version 'x.x-SNAPSHOT'
    ...
}
```

* Refresh gradle project with the 🔄 button in the gradle sidebar.
* In case the used gradle version is not compatible, update the `distributionUrl` in `gradle/wrapper/gradle-wrapper.propperties`
* Restart IDE
* Continue with the setup as described in the README.md
