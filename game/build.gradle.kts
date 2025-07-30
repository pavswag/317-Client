import java.security.MessageDigest
import java.io.File

group = "com.client"
version = "0.0.1"

dependencies {
    implementation(project(":runelite"))

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("com.google.guava:guava:30.0-android")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("commons-io:commons-io:2.7")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("com.dorkbox:Notify:3.7")
    implementation("com.google.code.gson:gson:2.8.9")

    // https://github.com/aschoerk/reflections8
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    implementation("com.displee:rs-cache-library:6.9")
    implementation("io.netty", "netty-buffer", "4.1.79.Final")
    implementation("one.util:streamex:0.8.1")
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("org.tukaani:xz:1.9")
    implementation("com.github.Vatuu:discord-rpc:1.6.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-core:2.8.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.8.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation(group = "com.google.inject", name = "guice", version = "5.0.1")

    // https://mvnrepository.com/artifact/me.tongfei/progressbar
    implementation("me.tongfei:progressbar:0.9.5")
    implementation("org.jetbrains:annotations:24.0.0")

    implementation("org.apache.commons:commons-text:1.8")

    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.20")

    implementation("org.reflections:reflections:0.10.2")

    val lombok = module("org.projectlombok", "lombok", "1.18.26")
    compileOnly(lombok)
    annotationProcessor(lombok)
    testCompileOnly(lombok)
    testAnnotationProcessor(lombok)
}

application {
    mainClass.set("Application")
}

tasks {

    register<JavaExec>("Run-Normal") {
        group = "Runelite"
        description = "Run Runelite in Normal Mode"
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("Application")
    }

    register<JavaExec>("Run-Development") {
        group = "Runelite"
        description = "Run Runelite in Development Mode"
        enableAssertions = true
        args = listOf("--developer-mode")
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("Application")
    }

}

tasks.register<proguard.gradle.ProGuardTask>("proguard") {

    val inJar = File("${buildDir}\\libs\\game-${version}-all.jar")
    val outJar = File("${buildDir}\\libs\\rlclient.jar")

    injars(inJar.absolutePath)
    outjars(outJar.absolutePath)

    configuration(File("proguard.conf"))

    configurations.getAt("runtimeClasspath").resolvedConfiguration.resolvedArtifacts.forEach {
        libraryjars(file(it.file.absolutePath))
    }

    val jmods = listOf(
        "java.base", "java.datatransfer",
        "java.desktop", "java.management",
        "jdk.jfr", "java.logging"
    )
    val jdkHome = System.getProperty("java.home")

    jmods.forEach {
        libraryjars("${jdkHome}/jmods/$it.jmod")
    }


    printmapping("obfuscation_map.txt")

}
// Define a custom task to calculate MD5 hash
tasks.register("calculateMd5Hash") {
    dependsOn("proguard")

    doLast {
        // File path of the output JAR after ProGuard task completes
        val outputJarPath = "${buildDir}/libs/rlclient.jar"

        // Calculate MD5 hash
        val md5Hash = calculateMd5(outputJarPath)

        // Print MD5 hash
        println("MD5 hash of $outputJarPath: $md5Hash")
    }
}

// Function to calculate MD5 hash of a file
fun calculateMd5(filePath: String): String {
    val digest = MessageDigest.getInstance("MD5")
    val fileBytes = File(filePath).readBytes()
    val hashBytes = digest.digest(fileBytes)
    return hashBytes.joinToString("") { byte -> "%02x".format(byte) }
}

