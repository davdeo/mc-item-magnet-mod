# Branching strategy


# Release Guide
* Create release branch
* Update deploy.yml + other files with latest release information
* Merge release branch to master
* Update is auto released in
  * github
  * modrinth
  * curseforge
* 

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
