import com.github.gradle.node.npm.task.NpxTask
plugins {
    java
    id("com.github.node-gradle.node") version("3.0.0-rc4")
}
repositories {
    maven (url = "http://oss.jfrog.org")
}
node {
    download.set(true)
    version.set("15.1.0")
}
val buildTask = tasks.register<NpxTask>("buildWebApp"){
    command.set("vue-cli-service")
    args.set(listOf("build"))
    dependsOn(tasks.npmInstall)
    inputs.dir(project.fileTree("src").exclude("**/*.spec.ts"))
    inputs.dir("node_modules")
    outputs.dir("${project.buildDir}/frontend")
}

sourceSets {
    java {
        main {
            resources {
                // This makes the processResources task automatically depend on the buildWebapp one
                srcDir(buildTask)
            }
        }
    }
}