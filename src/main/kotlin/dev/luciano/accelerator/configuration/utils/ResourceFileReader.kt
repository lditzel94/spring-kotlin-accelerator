package dev.luciano.accelerator.configuration.utils

import java.io.InputStreamReader
import kotlin.text.Charsets.UTF_8
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.util.FileCopyUtils

class ResourceFileReader() {
    companion object {
        suspend fun get(res: String, dispatcher: CoroutineDispatcher = Dispatchers.IO): String {
            val loader = DefaultResourceLoader();
            val resource = loader.getResource(res);
            val reader = withContext(dispatcher) {
                InputStreamReader(resource.inputStream, UTF_8.name())
            }
            return FileCopyUtils.copyToString(reader).trimIndent();
        }
    }
}