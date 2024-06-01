package com.client.cache

import kotlinx.coroutines.*
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import javax.swing.JOptionPane
import javax.xml.bind.DatatypeConverter
import kotlin.math.roundToInt
import kotlin.system.exitProcess
import java.io.IOException
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class CacheDownloader(
    val path: String,
    val url: String,
    var updateCheck: Boolean = true,
    val writeOnlineHash: Boolean = false,
    private val listener: Progress? = object : Progress() {
        override fun update(progress: Int, message: String) {
            print("\r [$progress] $message")
        }
    }
) {
    private val coroutineScope = CoroutineScope(Executors.newFixedThreadPool(4).asCoroutineDispatcher())
    private val deferredTasks = mutableListOf<Deferred<Unit>>() // Store Deferred objects

    var update: MutableList<String> = mutableListOf()

    init {
  /*      if (writeOnlineHash) {
            updateCheck = false
            writeHashes()
        }
        initialize()*/
    }

    private fun initialize() {
        listener?.update(0, "Looking for Updates...")

        if (updateFiles() && updateCheck) {
            listener?.update(0, "Updates found...")
            generateList()
            runBlocking {
                update.forEach {
                    val deferred = coroutineScope.async {
                        downloadAsync(url + it, path + it)
                    }
                    deferredTasks.add(deferred)
                    // Check if the update is sprites.zip or sounds.zip and delete the corresponding folder if necessary
                    if (it == "sprites.zip") {
                        val spritesFolder = File(path, "sprites")
                        if (spritesFolder.exists()) {
                            spritesFolder.deleteRecursively()
                        }
                    } else if (it == "sounds.zip") {
                        val soundsFolder = File(path, "sounds")
                        if (soundsFolder.exists()) {
                            soundsFolder.deleteRecursively()
                        }
                    }
                }

                // Wait for all downloads to finish
                deferredTasks.forEach { it.await() }

                // Now perform other operations
                recheckAndDownloadMissingFilesAsync()

                // Check if sprites.zip was downloaded and delete the contents of the sprites folder if necessary
                val spritesZipFile = File(path, "sprites.zip")
                if (spritesZipFile.exists()) {
                    val spritesFolder = File(path, "sprites")
                    if (!spritesFolder.exists()) {
                        checkAndExtractZip("sprites.zip", ".")
                    }
                }

                // Check if sounds.zip was downloaded and delete the contents of the sounds folder if necessary
                val soundsZipFile = File(path, "sounds.zip")
                if (soundsZipFile.exists()) {
                    val soundsFolder = File(path, "sounds")
                    if (!soundsFolder.exists()) {
                        checkAndExtractZip("sounds.zip", ".")
                    }
                }
            }
            writeHashes()
        }
    }

    private suspend fun downloadAsync(url: String, path: String) {
        val deferred = coroutineScope.async {
            download(url, path)
        }
        deferred.await()
    }

    private suspend fun recheckAndDownloadMissingFilesAsync() {
        val jsonParser = JSONParser()
        val link = URL(url + "online_hashes.json")
        val inputStream = link.openConnection().getInputStream()
        val obj: Any = jsonParser.parse(InputStreamReader(inputStream).readText())
        val list = obj as JSONArray

        val missingFiles = mutableListOf<String>()

        list.forEach {
            val raw: JSONObject = it as JSONObject
            val data = raw["data"] as JSONObject
            val name = data["name"] as String
            val hash = data["hash"] as String
            val location = data["location"] as String
            if (needsDownload(location, hash) && name != "hashes.json") {
                missingFiles.add(location)
            }
        }

        if (missingFiles.isNotEmpty()) {
            listener?.update(0, "Rechecking and downloading missing files...")
            missingFiles.forEach {
                val deferred = coroutineScope.async {
                    downloadAsync(url + it, path + it)
                }
                deferredTasks.add(deferred)
            }
            listener?.update(100, "Recheck and download complete")
        } else {
            listener?.update(100, "No missing files found")
        }

    }

    private fun generateList() {
        listener?.update(0, "Generating Patch List...")
        val jsonParser = JSONParser()

        val link = URL(url + "online_hashes.json")
        val inputStream = link.openConnection()
        val obj: Any = jsonParser.parse(InputStreamReader(inputStream.getInputStream()).readText())
        val list = obj as JSONArray

        list.forEach {
            val raw: JSONObject = it as JSONObject
            val data = raw["data"] as JSONObject
            val name = data["name"] as String
            val hash = data["hash"] as String
            val location = data["location"] as String
            if (needsDownload(location, hash) && name != "hashes.json") {
                update.add(location)
            }
        }
        listener?.size = update.size
    }

    private fun needsDownload(location: String, hash: String): Boolean {
        return if (!File(path, location).exists()) {
            true
        } else getHash(File(path, location).toPath()) != hash
    }

    private fun updateFiles(): Boolean {
        return if (!File(path, "hashes.json").exists()) {
            true
        } else {
            getOnlineHash(URL(url + "cache.php?name=online_hashes.json")) != getHash(File(path, "hashes.json").toPath())
        }
    }

    private fun getOnlineHash(url: URL): String {
        if ((url.openConnection() as HttpURLConnection).responseCode == 404) {
            error("Could not locate cache.php at $url")
            exitProcess(0)
        }
        val hash = InputStreamReader(url.openStream()).readText()
        if (hash.isBlank()) {
            error("Could not find the file you requested")
            exitProcess(0)
        }
        return hash
    }

    private fun getHash(file: Path): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(Files.readAllBytes(file))
        val digest = md.digest()
        return DatatypeConverter.printHexBinary(digest).toLowerCase()
    }

    private fun download(name: String, path: String) {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val url = URL(name.replace(" ", "%20"))
            val userAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"
            val con: URLConnection = url.openConnection()
            con.setRequestProperty("User-Agent", userAgent)
            val contentLength: Int = con.contentLength

            inputStream = BufferedInputStream(con.getInputStream())
            File(path.substringBeforeLast("/")).mkdirs()
            outputStream = BufferedOutputStream(FileOutputStream(path))

            val buffer = ByteArray(4096*2)
            var length: Int
            var downloaded = 0

            while (inputStream.read(buffer).also { length = it } != -1) {
                outputStream.write(buffer, 0, length)
                downloaded += length

                listener?.fileName = name.substringAfterLast("/")
                listener?.link = name
                listener?.update(
                    ((downloaded.toDouble() * 100.0) / (contentLength * 1.0)).toInt(),
                    "Downloading ${name.substring(name.lastIndexOf('/') + 1)}..."
                )
            }
        } catch (ex: Exception) {
            error(ex)
        } finally {
            // Close streams in finally block to ensure they are closed even if an exception occurs
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun checkAndExtractZip(zipFileName: String, outputDirectoryName: String) {
        val zipFile = File(path, zipFileName)
        if (zipFile.exists()) {
            println("Extracting $zipFileName")
            println("Extracting ${zipFile.absoluteFile}")
            val outputDirectory = File(path, outputDirectoryName)
            extractZipFile(zipFile, outputDirectory)
        }
    }
    private fun extractZipFile(zipFile: File, outputDirectory: File) {
        println("Extracting ${zipFile.absoluteFile}")
        ZipInputStream(FileInputStream(zipFile)).use { zipStream ->
            var entry = zipStream.nextEntry
            while (entry != null) {
                val entryFile = File(outputDirectory, entry.name)
                if (entry.isDirectory) {
                    entryFile.mkdirs()
                } else {
                    entryFile.parentFile.mkdirs()
                    FileOutputStream(entryFile).use { fos ->
                        zipStream.copyTo(fos)
                    }
                }
                entry = zipStream.nextEntry
            }
        }
    }

    private fun writeHashes() {
        val list = JSONArray()
        val rootPath = Paths.get(path)
        Files.walk(rootPath)
            .filter { !Files.isDirectory(it) }
            .filter { file ->
                val fileName = file.fileName.toString()
                val parentDir = file.parent?.toString()
                // Exclude specific directories
                val isExcludedDir = parentDir?.contains("sprites") ?: false ||
                        parentDir?.contains("okhttp") ?: false ||
                        parentDir?.contains("error_logs") ?: false||
                        parentDir?.contains("sounds") ?: false
                // Exclude specific files
                val isExcludedFile = fileName == "hashes.json" ||
                        fileName == "accounts.dat" ||
                        fileName == "uid.dat"
                !isExcludedDir && !isExcludedFile
            }
            .forEach {
                // Process the files here
                val data = JSONObject()
                data["name"] = it.fileName.toString()
                data["location"] = rootPath.relativize(it).toString().replace("\\", "/") // Use Path::relativize to get the relative path
                data["hash"] = getHash(it)
                val write = JSONObject()
                write["data"] = data
                list.add(write)
            }
        try {
            FileWriter(if (writeOnlineHash) "online_hashes.json" else path + "hashes.json").use { file ->
                file.write(list.toJSONString())
                file.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (writeOnlineHash) {
            exitProcess(0)
        }
    }

    fun awaitCompletion() {
        runBlocking {
            coroutineScope {
                deferredTasks.forEach { deferred ->
                    deferred.await() // Wait for each coroutine to complete
                }
            }
        }
    }
}
