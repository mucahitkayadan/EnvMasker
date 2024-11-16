package org.example.envmasker.file

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object EnvFileType : FileType {
    override fun getName() = "Env file"
    override fun getDescription() = "Environment variables file"
    override fun getDefaultExtension() = "env"
    override fun getIcon(): Icon = IconLoader.getIcon("/icons/env.svg", javaClass)
    override fun isBinary() = false
    override fun isReadOnly() = false
}
