plugins {
  id("java-gradle-plugin")
  kotlin("jvm")
  id("com.github.gmazzo.buildconfig")
}

dependencies {
  implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
  val project = project(":kotlin-fe-plugin")
  packageName(project.group.toString())
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${project.group}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${project.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${project.version}\"")
}

gradlePlugin {
  plugins {
    create("kotlinIrPluginTemplate") {
      id = rootProject.extra["kotlin_plugin_id"] as String
      displayName = "Kotlin Frontend Plugin Test"
      description = "Kotlin Frontend Plugin Test"
      implementationClass = "org.unibo.plugintest.TemplateGradlePlugin"
    }
  }
}
